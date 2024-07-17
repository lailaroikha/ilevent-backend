package com.ilevent.ilevent_backend.users.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilevent.ilevent_backend.responses.Response;
import com.ilevent.ilevent_backend.users.dto.*;
import com.ilevent.ilevent_backend.users.entity.Users;
import com.ilevent.ilevent_backend.users.service.UserService;
import com.ilevent.ilevent_backend.auth.helper.Claims;
import jakarta.annotation.security.RolesAllowed;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/users")
@Validated
@Log
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {

        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@RequestBody RegisterRequestDto registerRequestDto) {
        RegisterResponseDto response = userService.register(registerRequestDto);
        return ResponseEntity.ok(response);
    }

    @RolesAllowed({"ROLE_PERSONAL", "ROLE_ORGANIZER"})
    @PutMapping(value = "/profile/updated", consumes = { "multipart/form-data" })
    public ResponseEntity<?> updateProfile(@RequestPart("updateProfileDto") String updateProfileDtoString,
                                           @RequestPart("profilePicture") MultipartFile profilePicture) {
        try {
            var claims = Claims.getClaimsFromJwt();
            var email = (String) claims.get("sub");
            log.info("Claims are: " + claims);
            log.info("User profile requested for user: " + email);

            // Convert JSON string to UpdateProfileDto
            ObjectMapper objectMapper = new ObjectMapper();
            UpdateProfileDto updateProfileDto = objectMapper.readValue(updateProfileDtoString, UpdateProfileDto.class);

            var updatedUserDto = userService.updateProfile(email, updateProfileDto, profilePicture);
            return Response.success("User profile updated successfully", updatedUserDto);
        } catch (IOException e) {
            log.severe("Error processing JSON: " + e.getMessage());
            return ResponseEntity.badRequest().body("Invalid JSON format");
        } catch (Exception e) {
            log.severe("Error updating profile: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating profile");
        }
    }

    @GetMapping("/referralcode/{id}")
    public ResponseEntity<ReferralResponseDto> getUserById(@PathVariable Long id) {
       Users user =userService.getUserById(id);
        ReferralResponseDto response = ReferralResponseDto.fromEntity(user);
        return ResponseEntity.ok(response);
    }




    @RolesAllowed({"ROLE_PERSONAL", "ROLE_ORGANIZER"})
    @PostMapping("/profile/picture")
    public ResponseEntity<?> uploadProfilePicture(@RequestParam("file") MultipartFile file) {
        var claims = Claims.getClaimsFromJwt();
        var email = (String) claims.get("sub");
        log.info("Profile picture upload request received for user: " + email);
        String imageUrl = userService.uploadProfilePicture(email, file);
        return Response.success("Profile picture uploaded successfully", imageUrl);
    }

    @RolesAllowed({"ROLE_PERSONAL", "ROLE_ORGANIZER"})
    @GetMapping("/profile")
    public ResponseEntity<ProfileResponseDto> getProfile() {
        try {
            var claims = Claims.getClaimsFromJwt();
            var userId = Long.parseLong(claims.get("userId").toString());
            ProfileResponseDto response = userService.getProfile(userId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ProfileResponseDto());
        }
    }


}
