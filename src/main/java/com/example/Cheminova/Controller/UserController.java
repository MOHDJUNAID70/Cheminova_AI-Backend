package com.example.Cheminova.Controller;

import com.example.Cheminova.DTOs.Request.UpdateProfileRequest;
import com.example.Cheminova.DTOs.Response.UserResponse;
import com.example.Cheminova.Repository.UserRepository;
import com.example.Cheminova.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController  {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getProfile(Authentication authentication) {
        return ResponseEntity.ok(userService.getProfile(authentication.getName()));
    }

    @PatchMapping("/update-profile")
    public ResponseEntity<String> updateProfile(@RequestBody UpdateProfileRequest request, Authentication authentication) {
        userService.updateProfile(request, authentication.getName());
        return ResponseEntity.ok("Profile updated successfully");
    }

    @DeleteMapping("/deactivate-profile")
    public ResponseEntity<String> deleteProfile(@RequestParam("Id") Integer id) {
        userService.deleteProfileById(id);
        return ResponseEntity.ok("User Profile Deactivated successfully");
    }

    @DeleteMapping("/delete-account")
    public ResponseEntity<String> deleteAccount(@RequestHeader("Authorization") String token, Authentication authentication) {
        String userToken = token.substring(7);
        userService.deleteAccount(authentication.getName(), userToken);
        return ResponseEntity.ok("Account deleted successfully");
    }
}
