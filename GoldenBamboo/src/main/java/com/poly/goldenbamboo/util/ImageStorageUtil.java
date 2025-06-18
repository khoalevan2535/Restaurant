package com.poly.goldenbamboo.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.web.multipart.MultipartFile;

public class ImageStorageUtil {
    private static final String UPLOAD_DIR = "d:/uploads";

    public static String storeImage(MultipartFile imageFile) {
        try {
            // Tạo tên file duy nhất
            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Không lưu được file hình ảnh: " + e.getMessage());
        }
    }
}
