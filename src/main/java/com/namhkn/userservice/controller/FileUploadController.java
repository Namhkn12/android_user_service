package com.namhkn.userservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//Can't be used to receive image for now
@RestController
@RequestMapping("/api/images")
public class FileUploadController {

    @Value("${file.image-dir}")
    private String uploadDir;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
//        String contentType = file.getContentType();
//        String extension = "";
//        if (contentType != null) {
//            // Add more if needed
//            extension = switch (contentType) {
//                case "image/png" -> ".png";
//                case "image/jpeg" -> ".jpg";
//                case "image/webp" -> ".webp";
//                default -> extension;
//            };
//        }
//        String originalFileName = file.getOriginalFilename();
//        String fileName;
//        // Fallback if original filename has extension
//        if (originalFileName != null && originalFileName.contains(".")) {
//            fileName = file.getOriginalFilename();
//        } else {
//            fileName = originalFileName + extension;
//        }
        Path filePath = Paths.get(uploadDir + "\\" + file.getOriginalFilename());
        Files.write(filePath, file.getBytes());
        return ResponseEntity.ok("Uploaded: " + file.getOriginalFilename());
    }
}
