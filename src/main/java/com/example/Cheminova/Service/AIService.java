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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
    @Autowired
    private ObjectMapper objectMapper;

    @Transactional
    public AIResponse generateLearningPath(InputRequest userInput, String name) {
        Users user=userRepository.findByEmail(name);

        Map<String, Integer> sortedSkills=new TreeMap<>(userInput.getSkills());
        String skills=objectMapper.writeValueAsString(sortedSkills);

        LearningPath existPath=learningPathRepository.findByUserAndInputGoalAndInputDailyHoursAndInputSkills(
                user,
                userInput.getGoal(),
                userInput.getDaily_study_hours(),
                skills
        );

        if(existPath!=null){
            return learningPathMapper.toResponse(existPath);
        }

        String url = this.Base_url + "/generate-path";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<InputRequest> entity = new HttpEntity<>(userInput, headers);

        AIResponse response=restTemplate.postForEntity(url, entity, AIResponse.class).getBody();

        if(response==null){
            throw new CustomException("Failed to generate learning path. Please try again.");
        }

        learningPathRepository.save(learningPathMapper.toEntity(response, user, skills, userInput.getGoal(), userInput.getDaily_study_hours()));

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
