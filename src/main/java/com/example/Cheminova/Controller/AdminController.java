package com.example.Cheminova.Controller;

import com.example.Cheminova.DTOs.Response.AIResponse;
import com.example.Cheminova.DTOs.Response.UserDetailToAdmin;
import com.example.Cheminova.DTOs.Response.UserResponse;
import com.example.Cheminova.Enum.Role;
import com.example.Cheminova.Enum.UserStatus;
import com.example.Cheminova.Model.Users;
import com.example.Cheminova.Repository.LearningPathRepository;
import com.example.Cheminova.Repository.UserRepository;
import com.example.Cheminova.Service.AIService;
import com.example.Cheminova.Service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin Controller", description = "Endpoints for admin operations")
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private AIService aiService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LearningPathRepository learningPathRepository;

    @GetMapping("/all-users")
    public Page<UserDetailToAdmin> getAllUsers(
            @RequestParam(defaultValue = "1", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size,
            @RequestParam(defaultValue = "id", required = false) String sortBy,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge,
            @RequestParam(required = false) Role role,
            @RequestParam(required = false)UserStatus status
    )
    {
        Pageable pageable = PageRequest.of(page-1, size, Sort.by(sortBy).ascending());
        return userService.getAllUsers(pageable, name, minAge, maxAge, role, status);
    }

    @GetMapping("/all-generated-path")
    public Page<AIResponse> AllGeneratedPath(
            @RequestParam(defaultValue = "1", required = false) int page,
            @RequestParam(defaultValue = "5", required = false) int size,
            @RequestParam(defaultValue = "createdAt", required = false) String sortBy,
            @RequestParam(required = false) String goal,
            @RequestParam(required = false) String name
            )
    {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(sortBy).descending());
        return aiService.AllGeneratedPath(pageable, goal, name);
    }

    @GetMapping("/stats")
    public ResponseEntity<?> getStats() {
        long totalUsers=userRepository.count();
        long activeUsers=userRepository.countByStatus(UserStatus.ACTIVE);
        long totalPaths=learningPathRepository.count();

        Map<String, Long> stats = Map.of(
                "totalUsers", totalUsers,
                "activeUsers", activeUsers,
                "totalPaths", totalPaths
        );
        return ResponseEntity.ok(stats);
    }

    @DeleteMapping("/deactivate-profile")
    public ResponseEntity<String> deleteProfile(@RequestParam("Id") Integer id) {
        userService.deleteProfileById(id);
        return ResponseEntity.ok("User Profile Deactivated successfully");
    }

    @PostMapping("/activate-user")
    public ResponseEntity<String> activeUser(@RequestParam("Id") Integer id) {
        userService.activeUser(id);
        return ResponseEntity.ok("User Profile Activated successfully");
    }


}
