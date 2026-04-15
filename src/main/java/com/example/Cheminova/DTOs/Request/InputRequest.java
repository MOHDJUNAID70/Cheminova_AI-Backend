package com.example.Cheminova.DTOs.Request;

import lombok.Data;

import java.util.Map;

@Data
public class InputRequest {
    Map<String, Integer> skills;
    private String goal;
    private Integer daily_study_hours;
}
