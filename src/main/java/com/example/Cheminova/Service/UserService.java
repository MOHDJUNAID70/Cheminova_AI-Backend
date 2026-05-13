package com.example.Cheminova.Service;

import com.example.Cheminova.DTOs.Request.UpdateProfileRequest;
import com.example.Cheminova.DTOs.Response.UserDetailToAdmin;
import com.example.Cheminova.DTOs.Response.UserResponse;
import com.example.Cheminova.Enum.Role;
import com.example.Cheminova.Enum.UserStatus;
import com.example.Cheminova.Exception.CustomException;
import com.example.Cheminova.JWT.JwtService;
import com.example.Cheminova.Mapper.UserMapper;
import com.example.Cheminova.Model.Users;
import com.example.Cheminova.Repository.UserRepository;
import com.example.Cheminova.Specification.UsersSpecification;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private TokenBlacklistService blacklistService;



    public UserResponse getProfile(String name) {
        Users user=userRepository.findByEmail(name);

        return userMapper.toUserResponse(user);
    }

    @Transactional
    public void updateProfile(UpdateProfileRequest request, String name) {
        Users user=userRepository.findByEmail(name);

        userMapper.updateProfileToUser(request, user);
        userRepository.save(user);
    }

    @Transactional
    public void deleteProfileById(Integer id) {
        Users user=userRepository.findById(id).orElseThrow(
                ()-> new CustomException("User not found with user_id: " + id)
        );
        user.setStatus(UserStatus.INACTIVE);
        userRepository.save(user);
    }

    @Transactional
    public void deleteAccount(String name, String token) {
        Users user=userRepository.findByEmail(name);

        long expirationTime=jwtService.getExpiration(token);

        blacklistService.blacklistToken(token, expirationTime);

        userRepository.delete(user);
    }

    public Page<UserDetailToAdmin> getAllUsers(Pageable pageable, String name, Integer minAge, Integer maxAge, Role role, UserStatus status) {
        Specification<Users> spec= UsersSpecification.getSpecification(name, minAge, maxAge, role, status);
        return userRepository.findAll(spec, pageable).map(userMapper::toUserDetailToAdmin);
    }
}
