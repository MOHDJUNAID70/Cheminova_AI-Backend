package com.example.Cheminova.Mapper;


import com.example.Cheminova.DTOs.Response.AIResponse;
import com.example.Cheminova.Model.LearningPath;
import com.example.Cheminova.Model.Users;
import com.fasterxml.jackson.core.type.TypeReference;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LearningPathMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "suggestedGoal", source = "response.suggested_goal")
    @Mapping(target = "confidenceScore", source = "response.confidence_score")
    @Mapping(target = "skillMatchPercentage", source = "response.skill_match_percentage")
    @Mapping(target = "matchAnalysis", source = "response.match_analysis")
    @Mapping(target = "estimatedTotalHours", source = "response.timeline_estimation.estimated_total_hours")
    @Mapping(target = "estimatedDurationWeeks", source = "response.timeline_estimation.estimated_duration_weeks")
    @Mapping(target = "topCareerMatches", expression = "java(toJson(response.getTop_career_matches()))")
    @Mapping(target = "missingSkills", expression = "java(toJson(response.getMissing_skills()))")
    @Mapping(target = "matchedSkills", expression = "java(toJson(response.getMatched_skills()))")
    @Mapping(target = "learningPath", expression = "java(toJson(response.getLearning_path()))")
    @Mapping(target = "recommendedCourses", expression = "java(toJson(response.getRecommended_courses()))")

    LearningPath toEntity(AIResponse response, Users user);

    default String toJson(Object obj) {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Mapping(target = "suggested_goal", source = "learningPath.suggestedGoal")
    @Mapping(target = "confidence_score", source = "learningPath.confidenceScore")
    @Mapping(target = "skill_match_percentage", source = "learningPath.skillMatchPercentage")
    @Mapping(target = "match_analysis", source = "learningPath.matchAnalysis")
    @Mapping(target = "timeline_estimation.estimated_total_hours", source = "learningPath.estimatedTotalHours")
    @Mapping(target = "timeline_estimation.estimated_duration_weeks", source = "learningPath.estimatedDurationWeeks")
    @Mapping(target = "top_career_matches", expression = "java(fromJson(learningPath.getTopCareerMatches(), " +
            "new com.fasterxml.jackson.core.type.TypeReference<java.util.List<com.example.Cheminova.DTOs.Response.CareerMatch>>() {}))")
    @Mapping(target = "missing_skills", expression = "java(fromJson(learningPath.getMissingSkills(), " +
            "new com.fasterxml.jackson.core.type.TypeReference<java.util.List<String>>() {}))")
    @Mapping(target = "matched_skills", expression = "java(fromJson(learningPath.getMatchedSkills(), " +
            "new com.fasterxml.jackson.core.type.TypeReference<java.util.List<String>>() {}))")
    @Mapping(target = "learning_path", expression = "java(fromJson(learningPath.getLearningPath(), " +
            "new com.fasterxml.jackson.core.type.TypeReference<java.util.List<com.example.Cheminova.DTOs.Response.LearningStep>>() {}))")
    @Mapping(target = "recommended_courses", expression = "java(fromJson(learningPath.getRecommendedCourses(), " +
            "new com.fasterxml.jackson.core.type.TypeReference<java.util.List<com.example.Cheminova.DTOs.Response.Course>>() {}))")
    @Mapping(target = "created_at", source = "learningPath.createdAt")
    AIResponse toResponse(LearningPath learningPath);

    default <T> T fromJson(String json, TypeReference<T> typeReference) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().readValue(json, typeReference);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }
}
