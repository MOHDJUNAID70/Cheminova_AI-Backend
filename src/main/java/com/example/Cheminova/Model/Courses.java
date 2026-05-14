package com.example.Cheminova.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Courses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String description;
    private String category;
    private Integer duration;
    private Integer lecture;
    private Double rating=0.0;
    private Integer students=0;
    private Double price;
    private String image;

    @Column(columnDefinition = "TEXT")
    private String skills;

    private LocalDateTime created;
    private LocalDateTime updated;

    @PrePersist
    public void prePersist() {
        this.created = LocalDateTime.now();
        this.updated = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updated = LocalDateTime.now();
    }
}
