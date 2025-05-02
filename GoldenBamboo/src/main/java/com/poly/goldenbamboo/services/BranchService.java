package com.poly.goldenbamboo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.goldenbamboo.dtos.BranchDTO;
import com.poly.goldenbamboo.entities.BranchEntity;
import com.poly.goldenbamboo.mappers.BranchMapper;
import com.poly.goldenbamboo.repositories.BranchJPA;
@Service
public class BranchService {

    @Autowired
    private BranchJPA branchJPA;

    @Autowired
    private BranchMapper branchMapper;
    
    public List<BranchEntity> getActiveBranches() {
        return branchJPA.findByStatusTrue();
    }

    public List<BranchDTO> getAllBranch() {
        return branchJPA.findAll().stream()
                .map(branchMapper::toDTO)
                .toList();
    }

    public BranchDTO getBranchById(Integer id) {
        return branchJPA.findById(id)
                .map(branchMapper::toDTO)
                .orElse(null);
    }

    public BranchDTO createBranch(BranchDTO branchDTO) {
        BranchEntity entity = branchMapper.toEntity(branchDTO);
        BranchEntity saved = branchJPA.save(entity);
        return branchMapper.toDTO(saved);
    }

    public BranchDTO updateBranch(Integer id, BranchDTO dto) {
        return branchJPA.findById(id)
                .map(existing -> {
                    existing.setName(dto.getName());
                    existing.setAddress(dto.getAddress());
                    existing.setDescription(dto.getDescription());
                    existing.setStatus(dto.isStatus());
                    return branchMapper.toDTO(branchJPA.save(existing));
                })
                .orElse(null);
    }

    public boolean deleteBranchById(Integer id) {
        if (branchJPA.existsById(id)) {
            branchJPA.deleteById(id);
            return true;
        }
        return false;
    }
}
