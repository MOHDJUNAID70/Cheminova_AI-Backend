package com.example.Cheminova.Specification;


import com.example.Cheminova.Model.LearningPath;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class LearningPathSpecification {

    public static Specification<LearningPath> getSpecification(String goal) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (goal != null && !goal.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("suggested_goal")), "%" + goal.toLowerCase() + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
