package com.example.Cheminova.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class LearningPath {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",  nullable = false)
    @JsonIgnore
    private Users user;

    private String suggestedGoal;

    private Double confidenceScore;

    private Integer skillMatchPercentage;

    private String matchAnalysis;

    private Integer estimatedTotalHours;

    private Double estimatedDurationWeeks;

    // Store lists as JSON strings in DB
    @Column(columnDefinition = "TEXT")
    private String topCareerMatches;    // JSON string

    @Column(columnDefinition = "TEXT")
    private String missingSkills;       // JSON string

    @Column(columnDefinition = "TEXT")
    private String matchedSkills;       // JSON string

    @Column(columnDefinition = "TEXT")
    private String learningPath;        // JSON string

    @Column(columnDefinition = "TEXT")
    private String recommendedCourses;     // JSON string

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

}
