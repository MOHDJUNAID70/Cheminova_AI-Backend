package com.example.Cheminova.DTOs.Request;

import lombok.Data;

@Data
public class CourseRequest {
    private String title;
    private String description;
    private String category;
    private Integer duration;
    private Integer lecture;
    private Double price;
    private String[] skills;
}
