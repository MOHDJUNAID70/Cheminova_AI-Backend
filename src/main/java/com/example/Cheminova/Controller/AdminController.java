package com.example.Cheminova.Controller;

import com.example.Cheminova.DTOs.Response.AIResponse;
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

@RestController
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin Controller", description = "Endpoints for admin operations")
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private AIService aiService;

    @GetMapping("/all-generated-path")
    public Page<AIResponse> AllGeneratedPath(
            @RequestParam(defaultValue = "1", required = false) int page,
            @RequestParam(defaultValue = "5", required = false) int size,
            @RequestParam(defaultValue = "createdAt", required = false) String sortBy,
            @RequestParam(required = false) String goal
    )
    {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(sortBy).descending());
        return aiService.AllGeneratedPath(pageable, goal);
    }

    @DeleteMapping("/deactivate-profile")
    public ResponseEntity<String> deleteProfile(@RequestParam("Id") Integer id) {
        userService.deleteProfileById(id);
        return ResponseEntity.ok("User Profile Deactivated successfully");
    }
}
