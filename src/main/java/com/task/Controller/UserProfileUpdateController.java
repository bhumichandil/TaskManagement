package com.task.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.task.ENUM.Role;
import com.task.Entity.UserProfileUpdate;
import com.task.Service.UserProfileUpdateService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class UserProfileUpdateController {

    private final UserProfileUpdateService userProfileService = new UserProfileUpdateService();

    // ✅ Update user profile
    @PutMapping("/update/{email}")
    public ResponseEntity<String> updateUserProfile(
            @PathVariable String email,
            @RequestBody UserProfileUpdate profile) {

        return ResponseEntity.ok(
                userProfileService.updateUserProfile(email, profile)
        );
    }

    // ✅ Get all profiles
    @GetMapping("/all")
    public ResponseEntity<List<UserProfileUpdate>> getAllUserProfile() {
        return ResponseEntity.ok(userProfileService.getAllProfile());
    }

    // ✅ Get profile by email
    @GetMapping("/{email}")
    public ResponseEntity<UserProfileUpdate> getProfileByEmail(
            @PathVariable("email") String userEmail) {

        return ResponseEntity.ok(userProfileService.getProfileByEmail(userEmail));
    }

    // ✅ Update user role
    @PutMapping("/update-role")
    public ResponseEntity<UserProfileUpdate> updateUserRole(
            @RequestParam String userEmail,
            @RequestParam Role role) {

        return ResponseEntity.ok(
                userProfileService.updateRole(userEmail, role)
        );
    }
}
