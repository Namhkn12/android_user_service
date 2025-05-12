package com.namhkn.userservice.controller;

import com.namhkn.userservice.dto.*;
import com.namhkn.userservice.model.UserInfo;
import com.namhkn.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    /**
     * Get user info, image not included
     * @param id id of the user (int for now)
     * @return user info, image not included
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") String id) {
        try {
            UserInfo userInfo = userService.getUser(id);
            UserDTO userDTO = new UserDTO(userInfo.getId(), userInfo.getDisplayName(), userInfo.getAddressList(), userInfo.getPhoneNumber(), userInfo.getGender(), userInfo.getDateOfBirth());
            return ResponseEntity.ok(userDTO);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PutMapping("/{id}/update/name")
    public ResponseEntity<?> updateName(@PathVariable("id") int id, @RequestBody UpdateNameRequest request) {
        userService.updateName(id, request.getDisplayName());
        return ResponseEntity.ok(request);
    }

    @PutMapping("/{id}/update/gender")
    public ResponseEntity<?> updateGender(@PathVariable("id") int id, @RequestBody UpdateGenderRequest request) {
        userService.updateGender(id, request.getGender());
        return ResponseEntity.ok(request);
    }

    @PutMapping("/{id}/update/dob")
    public ResponseEntity<?> updateDOB(@PathVariable("id") int id, @RequestBody UpdateDOBRequest request) {
        userService.updateDOB(id, request.getDateOfBirth());
        return ResponseEntity.ok(request);
    }

    @PutMapping("/{id}/update/phone")
    public ResponseEntity<?> updatePhone(@PathVariable("id") int id, @RequestBody UpdatePhoneRequest request) {
        userService.updatePhoneNumber(id, request.getPhoneNumber());
        return ResponseEntity.ok(request);
    }

    @PutMapping("/{id}/update/addresses")
    public ResponseEntity<?> updateAddresses(@PathVariable("id") int id, @RequestBody UpdateAddressesRequest request) {
        userService.updateAddresses(id, request.getAddresses());
        return ResponseEntity.ok(request);
    }

    @PutMapping("/{id}/update/address")
    public ResponseEntity<?> updateAddress(@PathVariable("id") int id, @RequestBody UserAddressDTO request) {
        userService.updateAddress(id, request);
        return ResponseEntity.ok(request);
    }

    @PutMapping("/{id}/update/password")
    public ResponseEntity<?> updatePassword(@PathVariable("id") int id, @RequestBody UpdatePasswordRequest request) {
        userService.updatePassword(id, request);
        return ResponseEntity.ok(request);
    }
}
