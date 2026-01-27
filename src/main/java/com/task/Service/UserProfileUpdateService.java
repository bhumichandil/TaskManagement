package com.task.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.task.ENUM.Role;
import com.task.Entity.UserProfileUpdate;
import com.task.Repository.UserProfileUpdateRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserProfileUpdateService {

    private final UserProfileUpdateRepository userProfileRepo = null;

    public String updateUserProfile(String userEmail, UserProfileUpdate profile) {

        UserProfileUpdate user = userProfileRepo.findByUserEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUserName(profile.getUserName());
        user.setOrganizationName(profile.getOrganizationName());
        user.setDepartment(profile.getDepartment());
        user.setDesignation(profile.getDesignation());
        user.setActive(true);

        userProfileRepo.save(user);

        return "User profile updated successfully";
    }

    public List<UserProfileUpdate> getAllProfile() {
        return userProfileRepo.findAll();
    }

    public UserProfileUpdate getProfileByEmail(String userEmail) {
        return userProfileRepo.findByUserEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserProfileUpdate updateRole(String userEmail, Role newRole) {

        UserProfileUpdate user = userProfileRepo.findByUserEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setRole(newRole);
        return userProfileRepo.save(user);
    }
}
