package com.example.Cheminova.DTOs.Response;


import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class AIResponse {
    private String suggested_goal;
    private Double confidence_score;
    private List<CareerMatch> top_career_matches;
    private Integer skill_match_percentage;
    private List<String> matched_skills;
    private List<String> missing_skills;
    private String match_analysis;
    private List<LearningStep> learning_path;
    private List<Course> recommended_courses;
    private LocalDateTime created_at;

    public Timeline timeline_estimation;
}