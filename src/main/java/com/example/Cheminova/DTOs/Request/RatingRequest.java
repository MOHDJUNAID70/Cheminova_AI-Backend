package com.example.Cheminova.DTOs.Request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RatingRequest {
    private Double rating;
}
