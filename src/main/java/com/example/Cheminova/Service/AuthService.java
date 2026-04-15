package com.example.Cheminova.Service;

import com.example.Cheminova.DTOs.Request.LoginRequest;
import com.example.Cheminova.DTOs.Request.RegisterRequest;
import com.example.Cheminova.Enum.UserStatus;
import com.example.Cheminova.Exception.CustomException;
import com.example.Cheminova.JWT.JwtService;
import com.example.Cheminova.Mapper.UserMapper;
import com.example.Cheminova.Model.Users;
import com.example.Cheminova.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Transactional
    public void register(RegisterRequest request) {
        Users userExist=userRepository.findByEmail(request.getEmail());

        if(userExist!=null){
            throw new CustomException("Email already Exist");
        }

        Users user = new Users();
        user.setName(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setAge(request.getAge());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        user.setRole(request.getRole());
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);
    }

    @Transactional
    public String verifyUser(LoginRequest request) {
        Users user=userRepository.findByEmail(request.getEmail());

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword())
        );

        return jwtService.generateKey(user.getEmail());
    }
}
