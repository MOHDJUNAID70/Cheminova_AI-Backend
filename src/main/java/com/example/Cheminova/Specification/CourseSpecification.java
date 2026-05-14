package com.example.Cheminova.Specification;

import com.example.Cheminova.Model.Courses;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CourseSpecification {
    public static Specification<Courses> getSpecification(String title, Integer minDuration,
                                                          Integer maxDuration, Double minPrice, Double maxPrice)
    {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(title != null && !title.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
            }
            if(minDuration != null || maxDuration != null) {
                predicates.add(criteriaBuilder.and(
                        criteriaBuilder.greaterThanOrEqualTo(root.get("duration"), minDuration),
                        criteriaBuilder.lessThanOrEqualTo(root.get("duration"), maxDuration)
                ));
            }
            if(minPrice != null || maxPrice != null) {
                predicates.add(criteriaBuilder.and(
                        criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice),
                        criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice)
                ));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
