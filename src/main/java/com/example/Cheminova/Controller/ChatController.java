package com.example.Cheminova.Controller;

import com.example.Cheminova.DTOs.Request.ChatRequest;
import com.example.Cheminova.DTOs.Response.ChatResponse;
import com.example.Cheminova.Service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatController {
    @Autowired
    private ChatService chatService;

    @PostMapping("/chat")
    public ChatResponse sendMessage(@RequestBody ChatRequest chatRequest) {
        return chatService.sendMessage(chatRequest.getMessage());
    }
}
