package com.example.Cheminova.Service;

import com.example.Cheminova.DTOs.Response.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.ResponseEntity.internalServerError;

@Service
public class ChatService {

    private RestTemplate restTemplate;

    @Value("${chat.api.url}")
    private String chatApiUrl;

    public ChatResponse sendMessage(String message) {
        String url=chatApiUrl+"/chat";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(message, headers);

        try {
            ResponseEntity<ChatResponse> response = restTemplate.postForEntity(
                    url,
                    entity,
                    ChatResponse.class
            );
            return response.getBody();

        } catch (Exception e) {
            return new ChatResponse() {{
                setResponse("Error: " + e.getMessage());
            }};
        }
    }
}