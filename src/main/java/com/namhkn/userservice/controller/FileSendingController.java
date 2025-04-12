package com.namhkn.userservice.controller;

import com.namhkn.userservice.dto.UserDTO;
import com.namhkn.userservice.model.User;
import com.namhkn.userservice.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/image/users/{id}")
public class FileSendingController {

    @Value("${file.image-dir}")
    private String uploadDir;

    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getUserImage(@PathVariable("id") String id) {
        try {
            User user = userService.getUser(id);
            String imgPathStr = user.getImgPath();
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
}
