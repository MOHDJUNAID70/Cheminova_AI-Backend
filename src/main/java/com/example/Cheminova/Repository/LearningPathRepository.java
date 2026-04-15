package com.example.Cheminova.Repository;

import com.example.Cheminova.Model.LearningPath;
import com.example.Cheminova.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface LearningPathRepository extends JpaRepository<LearningPath,Long> {

    List<LearningPath> findAllByUser(Users user);
}
