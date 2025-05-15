package com.namhkn.userservice.controller;

import com.namhkn.userservice.model.UserInfo;
import com.namhkn.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/image/users/{id}")
public class FileController {

    @Value("${file.image-dir}")
    private String uploadDir;

    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getUserImage(@PathVariable("id") String id) {
        try {
            UserInfo userInfo = userService.getUser(id);
            String imgPathStr = userInfo.getImgPath();
            Path imgPath = Path.of(uploadDir + "\\" + imgPathStr);
            String contentType = Files.probeContentType(imgPath);
            byte[] imgBytes = Files.readAllBytes(imgPath);
            if (contentType != null) {
                return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(imgBytes);
            }
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(imgBytes);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error getting user image: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> uploadUserImage(@PathVariable("id") int id, @RequestParam("image") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please upload a file.");
        }

        try {
            // Create the uploads directory if it doesn't exist
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Save the file to the server
            String extension = getFileExtensionFromContentType(file.getContentType());
            if (extension == null) {
                return ResponseEntity.badRequest().body("Must be an image png/jpg.");
            }
            String fileName = id + extension;
            Path filePath = uploadPath.resolve(fileName);
            Files.write(filePath, file.getBytes());

            return ResponseEntity.ok("File uploaded successfully: " + fileName);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to upload file: " + e.getMessage());
        }
    }

    private String getFileExtensionFromContentType(String contentType) {
        if (contentType == null) {
            return null;
        }
        return switch (contentType.toLowerCase()) {
            case "image/jpeg" -> ".jpg";
            case "image/png" -> ".png";
            default -> null; // Return empty or a default extension if unknown
        };
    }
}
