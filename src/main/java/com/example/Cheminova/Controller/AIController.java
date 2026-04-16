package com.example.Cheminova.Controller;


import com.example.Cheminova.DTOs.Response.AIResponse;
import com.example.Cheminova.DTOs.Request.InputRequest;
import com.example.Cheminova.Repository.LearningPathRepository;
import com.example.Cheminova.Service.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/user")
public class AIController {

    @Autowired
    private AIService aiService;

    @Autowired
    private LearningPathRepository learningPathRepository;

    @PostMapping("/learning-path")
    public ResponseEntity<AIResponse> careerPath(@RequestBody InputRequest userInput, Authentication authentication) {
        return ResponseEntity.ok(aiService.generateLearningPath(userInput, authentication.getName()));
    }

    @GetMapping("/generated-path")
    public List<AIResponse> careerPath(Authentication authentication) {
        return aiService.getGeneratedPath(authentication.getName());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePath(@PathVariable Long id) {
        learningPathRepository.deleteById(id);
        return ResponseEntity.ok("Learning path with id " + id + " has been deleted.");
    }

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
}
