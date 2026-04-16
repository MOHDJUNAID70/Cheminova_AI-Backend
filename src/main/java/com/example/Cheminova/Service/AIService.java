package com.example.Cheminova.Service;

import com.example.Cheminova.DTOs.Response.AIResponse;
import com.example.Cheminova.DTOs.Request.InputRequest;
import com.example.Cheminova.Exception.CustomException;
import com.example.Cheminova.Mapper.LearningPathMapper;
import com.example.Cheminova.Model.LearningPath;
import com.example.Cheminova.Model.Users;
import com.example.Cheminova.Repository.LearningPathRepository;
import com.example.Cheminova.Repository.UserRepository;
import com.example.Cheminova.Specification.LearningPathSpecification;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Service
public class AIService {

    @Autowired
    private LearningPathMapper learningPathMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LearningPathRepository learningPathRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${ai.server.url}")
    private String Base_url;

    @Transactional
    public AIResponse generateLearningPath(InputRequest userInput, String name) {
        String url = this.Base_url + "/generate-path";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<InputRequest> entity = new HttpEntity<>(userInput, headers);

        AIResponse response=restTemplate.postForEntity(url, entity, AIResponse.class).getBody();

        Users user=userRepository.findByEmail(name);

        learningPathRepository.save(learningPathMapper.toEntity(response, user));

        return response;
    }

    public List<AIResponse> getGeneratedPath(String name) {
        Users user=userRepository.findByEmail(name);

        List<LearningPath> generatedPath=learningPathRepository.findAllByUser(user);
        return generatedPath.stream().map(learningPathMapper::toResponse).toList();
    }

    public Page<AIResponse> AllGeneratedPath(Pageable pageable, String goal) {
        Specification<LearningPath> spec = LearningPathSpecification.getSpecification(goal);
        return learningPathRepository.findAll(spec, pageable).map(learningPathMapper::toResponse);
    }
}
