package com.example.Cheminova.Mapper;

import com.example.Cheminova.DTOs.Response.CourseResponse;
import com.example.Cheminova.Model.Courses;
import com.fasterxml.jackson.core.type.TypeReference;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    @Mapping(target = "image", source = "course.image")
    @Mapping(target = "skills", expression = "java(fromJson(course.getSkills(), new com.fasterxml.jackson.core.type.TypeReference<String[]>() {}))")
    CourseResponse toCourseResponse(Courses course);

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
