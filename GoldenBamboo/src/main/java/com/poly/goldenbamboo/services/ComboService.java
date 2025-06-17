package com.poly.goldenbamboo.services;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.poly.goldenbamboo.GoldenBambooApplication;
import com.poly.goldenbamboo.entities.ComboEntity;
import com.poly.goldenbamboo.repositories.ComboJPA;

@Service
public class ComboService {
    
    private static final Logger logger = LoggerFactory.getLogger(ComboService.class);
    
    @Autowired
    private ComboJPA comboJPA;

    @Autowired
    private CloudinaryService cloudinaryService;

    private final GoldenBambooApplication goldenBambooApplication;

    public List<ComboEntity> searchCombos(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return comboJPA.findAll();
        }
        return null;
    }

    
    public ComboService(GoldenBambooApplication goldenBambooApplication) {
        this.goldenBambooApplication = goldenBambooApplication;
    }

    public List<ComboEntity> getAllCombo() {
        return comboJPA.findAll();
    }

    public ComboEntity getComboById(Integer id) {
        Optional<ComboEntity> optional = comboJPA.findById(id);
        return optional.orElse(null);
    }

    public ComboEntity createCombo(ComboEntity comboEntity, MultipartFile imageFile) throws IOException {
        validateCombo(comboEntity);
        
        if (imageFile != null && !imageFile.isEmpty()) {
            validateImageFile(imageFile);
            String imageUrl = cloudinaryService.uploadFile(imageFile);
            comboEntity.setImage(imageUrl);
        }
        
        comboEntity.setStatus(comboEntity.isStatus());
        return comboJPA.save(comboEntity);
    }

    public ComboEntity updateCombo(Integer id, ComboEntity updatedCombo, MultipartFile newImageFile) throws IOException {
        validateCombo(updatedCombo);
        
        Optional<ComboEntity> optionalCombo = comboJPA.findById(id);
        if (!optionalCombo.isPresent()) {
            throw new IllegalArgumentException("Combo not found with id: " + id);
        }

        ComboEntity existingCombo = optionalCombo.get();
        
        if (newImageFile != null && !newImageFile.isEmpty()) {
            validateImageFile(newImageFile);
            deleteOldImageIfExists(existingCombo.getImage());
            String newImageUrl = cloudinaryService.uploadFile(newImageFile);
            existingCombo.setImage(newImageUrl);
        }
        existingCombo.setName(updatedCombo.getName());
        existingCombo.setPrice(updatedCombo.getPrice());
        existingCombo.setDescription(updatedCombo.getDescription());
        existingCombo.setStatus(updatedCombo.isStatus());
        
        return comboJPA.save(existingCombo);
    }

    public void deleteCombo(Integer id) throws IOException {
        Optional<ComboEntity> optionalCombo = comboJPA.findById(id);
        if (!optionalCombo.isPresent()) {
            throw new IllegalArgumentException("Combo not found with id: " + id);
        }

        ComboEntity combo = optionalCombo.get();
        deleteOldImageIfExists(combo.getImage());
        comboJPA.deleteById(id);
    }

    private void deleteOldImageIfExists(String imageUrl) throws IOException {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            try {
                String publicId = extractPublicIdFromUrl(imageUrl);
                if (publicId != null) {
                    cloudinaryService.deleteFile(publicId);
                }
            } catch (Exception e) {
                logger.error("Failed to delete old image from Cloudinary: " + imageUrl, e);
                throw new IOException("Failed to delete old image", e);
            }
        }
    }

    private String extractPublicIdFromUrl(String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            return null;
        }
        
        try {
            String[] parts = imageUrl.split("/");

            int uploadIndex = -1;
            for (int i = 0; i < parts.length; i++) {
                if (parts[i].equals("upload")) {
                    uploadIndex = i;
                    break;
                }
            }
            
            if (uploadIndex == -1 || uploadIndex >= parts.length - 1) {
                return null;
            }

            String publicIdWithVersion = parts[uploadIndex + 1];
            
            if (publicIdWithVersion.startsWith("v")) {
                String[] idParts = publicIdWithVersion.split("/");
                if (idParts.length > 1) {
                    return idParts[1];
                }
                return null;
            }
            
            return publicIdWithVersion;
        } catch (Exception e) {
            logger.warn("Invalid Cloudinary URL format: " + imageUrl, e);
            return null;
        }
    }

    private void validateCombo(ComboEntity combo) {
        if (combo == null) {
            throw new IllegalArgumentException("Combo cannot be null");
        }
        if (combo.getName() == null || combo.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Combo name cannot be empty");
        }
        if (combo.getPrice() == null || combo.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Invalid combo price");
        }
    }

    private void validateImageFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Image file cannot be empty");
        }
        if (!file.getContentType().startsWith("image/")) {
            throw new IllegalArgumentException("Only image files are allowed");
        }
    }


    public List<ComboEntity> getDefaultCombosByBranch(Integer branchId) {
      //  return comboJPA.findDefaultMenuCombosByBranch(branchId);
    	return null;
    }
}