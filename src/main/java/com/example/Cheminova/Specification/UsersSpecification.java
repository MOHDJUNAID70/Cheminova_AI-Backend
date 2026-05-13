package com.example.Cheminova.Specification;


import com.example.Cheminova.Enum.Role;
import com.example.Cheminova.Enum.UserStatus;
import com.example.Cheminova.Model.Users;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;

public class UsersSpecification {
    public static Specification<Users> getSpecification(String name, Integer minAge, Integer maxAge, Role role, UserStatus status) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(name != null && !name.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }
            if(minAge != null && maxAge != null) {
                predicates.add(criteriaBuilder.and(
                        criteriaBuilder.greaterThanOrEqualTo(root.get("age"), minAge ),
                        criteriaBuilder.lessThanOrEqualTo(root.get("age"), maxAge ))
                );
            }
            if(role != null){
                predicates.add(criteriaBuilder.equal(root.get("role"), role));
            }
            if(status != null){
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
