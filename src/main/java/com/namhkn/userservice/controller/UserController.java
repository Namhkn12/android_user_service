package com.namhkn.userservice.controller;

import com.namhkn.userservice.dto.UserDTO;
import com.namhkn.userservice.model.User;
import com.namhkn.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users/{id}")
public class UserController {
    private final UserService userService;

    /**
     * Get user info, image not included
     * @param id id of the user (int for now)
     * @return user info, image not included
     */
    @GetMapping
    public ResponseEntity<?> getUser(@PathVariable("id") String id) {
        try {
            User user = userService.getUser(id);
            UserDTO userDTO = new UserDTO(user.getId(), user.getDisplayName(), user.getAddress(), user.getPhoneNumber());
            return ResponseEntity.ok(userDTO);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error getting user:" + e.getMessage());
        }
    }

}
