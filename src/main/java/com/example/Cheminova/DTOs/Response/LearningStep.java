package com.example.Cheminova.DTOs.Response;

import lombok.Data;

import java.util.List;

@Data
public class LearningStep {
    private Integer phase;
    private String title;
    private List<String> topics;
}
