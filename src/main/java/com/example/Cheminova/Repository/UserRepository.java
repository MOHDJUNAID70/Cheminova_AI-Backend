package com.example.Cheminova.Repository;

import com.example.Cheminova.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

     Users findByEmail(String email);

}
