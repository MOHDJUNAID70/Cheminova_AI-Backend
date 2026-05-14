package com.example.Cheminova.Repository;

import com.example.Cheminova.Model.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Courses, Integer>, JpaSpecificationExecutor<Courses> {

}
