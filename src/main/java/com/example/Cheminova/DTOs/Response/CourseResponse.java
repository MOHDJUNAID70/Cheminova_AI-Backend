package com.example.Cheminova.DTOs.Response;

import lombok.Data;

@Data
public class CourseResponse {

    private Integer id;

    private String title;
    private String description;
    private String category;
    private Integer duration;
    private Integer lecture;
    private Double rating;
    private Integer students;
    private Double price;
    private String image;
    private String[] skills;
}
