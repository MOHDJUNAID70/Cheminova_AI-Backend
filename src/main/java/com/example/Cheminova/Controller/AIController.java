package com.example.Cheminova.Controller;


import com.example.Cheminova.DTOs.Response.AIResponse;
import com.example.Cheminova.DTOs.Request.InputRequest;
import com.example.Cheminova.Service.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class AIController {

    @Autowired
    private AIService aiService;

    @PostMapping("/learning-path")
    public ResponseEntity<AIResponse> careerPath(@RequestBody InputRequest userInput, Authentication authentication) {
        return ResponseEntity.ok(aiService.generateLearningPath(userInput, authentication.getName()));
    }

    @GetMapping("/generated-path")
    public List<AIResponse> careerPath(Authentication authentication) {
        return aiService.getGeneratedPath(authentication.getName());
    }
}
