package com.namhkn.userservice.controller;

import com.namhkn.userservice.dto.*;
import com.namhkn.userservice.model.UserAddress;
import com.namhkn.userservice.model.UserInfo;
import com.namhkn.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            UserDTO userDTO = new UserDTO(userInfo.getId(), userInfo.getDisplayName(), userInfo.getAddressList(), userInfo.getPhoneNumber(), userInfo.getGender(), userInfo.getDateOfBirth(), userInfo.getEmail());
            return ResponseEntity.ok(userDTO);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @GetMapping("/{id}/address")
    public List<UserAddress> getAddressesOfUser(@PathVariable("id") int userId) {
        return userService.getAddressesOfUser(userId);
    }

    @PutMapping("/{id}/update/name")
    public ResponseEntity<?> updateName(@PathVariable("id") int id, @RequestBody UpdateNameRequest request) {
        userService.updateName(id, request.getDisplayName());
        return ResponseEntity.ok("Name updated successfully.");
    }

    @PutMapping("/{id}/update/gender")
    public ResponseEntity<?> updateGender(@PathVariable("id") int id, @RequestBody UpdateGenderRequest request) {
        userService.updateGender(id, request.getGender());
        return ResponseEntity.ok("Gender updated successfully.");
    }

    @PutMapping("/{id}/update/dob")
    public ResponseEntity<?> updateDOB(@PathVariable("id") int id, @RequestBody UpdateDOBRequest request) {
        userService.updateDOB(id, request.getDateOfBirth());
        return ResponseEntity.ok("Date of Birth updated successfully.");
    }

    @PutMapping("/{id}/update/phone")
    public ResponseEntity<?> updatePhone(@PathVariable("id") int id, @RequestBody UpdatePhoneRequest request) {
        userService.updatePhoneNumber(id, request.getPhoneNumber());
        return ResponseEntity.ok("Phone number updated successfully.");
    }

    @PutMapping("/{id}/update/addresses")
    public ResponseEntity<?> updateAddresses(@PathVariable("id") int id, @RequestBody UpdateAddressesRequest request) {
        userService.updateAddresses(id, request.getAddresses());
        return ResponseEntity.ok("Addresses updated successfully.");
    }

    @PutMapping("/{id}/update/address")
    public ResponseEntity<?> updateAddress(@PathVariable("id") int id, @RequestBody UserAddressDTO request) {
        userService.updateAddressById(id, request);
        return ResponseEntity.ok("Address id "+ id +" updated successfully.");
    }

    @PostMapping("/{id}/add/address")
    public void addAddress(@PathVariable("id") int userId, @RequestBody UserAddressDTO addressDTO) {
        userService.addAddress(userId, addressDTO);
    }

    @PutMapping("/remove/address/{id}")
    public void removeAddress(@PathVariable("id") int addressId) {
        userService.removeAddress(addressId);
    }

    @PutMapping("/update/email")
    public ResponseEntity<?> updateEmail(@RequestBody UpdateEmailRequest request) {
        boolean res = userService.updateEmail(request);
        if (res) return ResponseEntity.ok(true);
        return ResponseEntity.status(HttpStatusCode.valueOf(401)).body(false);
    }

    @PutMapping("/update/password")
    public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordRequest request) {
        boolean res = userService.updatePassword(request);
        if (res) return ResponseEntity.ok(true);
        return ResponseEntity.status(HttpStatusCode.valueOf(401)).body(false);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> requestPasswordChange(@RequestBody ChangePasswordRequest request) {
        boolean res = userService.requestChangePassword(request);
        if (res) return ResponseEntity.ok(true);
        return ResponseEntity.status(HttpStatusCode.valueOf(401)).body(false);
    }

    @PostMapping("/request-confirm-email")
    public ResponseEntity<?> confirmEmail(@RequestBody ConfirmEmailRequest request) {
        boolean res = userService.requestConfirmEmail(request);
        if (res) return ResponseEntity.ok(true);
        return ResponseEntity.status(HttpStatusCode.valueOf(401)).body(false);
    }

    @PostMapping("/verify-email")
    public ResponseEntity<?> verifyEmailCode(@RequestBody CodeRequest codeRequest) {
        boolean res = userService.verifyEmailCode(codeRequest.getUsername(), codeRequest.getCode());
        if (res) return ResponseEntity.ok(true);
        return ResponseEntity.status(HttpStatusCode.valueOf(401)).body(false);
    }
}
