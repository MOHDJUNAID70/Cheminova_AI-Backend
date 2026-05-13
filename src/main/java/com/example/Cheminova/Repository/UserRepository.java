package com.example.Cheminova.Repository;

import com.example.Cheminova.Enum.UserStatus;
import com.example.Cheminova.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<Users, Integer>, JpaSpecificationExecutor<Users> {

     Users findByEmail(String email);

    long countByStatus(UserStatus userStatus);
}
