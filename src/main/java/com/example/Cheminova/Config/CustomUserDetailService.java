package com.example.Cheminova.Config;

import com.example.Cheminova.Enum.UserStatus;
import com.example.Cheminova.Exception.CustomException;
import com.example.Cheminova.Model.Users;
import com.example.Cheminova.Repository.UserRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
     @Override
    public UserDetails loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {
        // Implement logic to load user details from the database using the email
        // You can use your UserRepository to fetch the user and return a UserDetails object

         Users user = userRepository.findByEmail(email);

         if(user.getStatus() == UserStatus.INACTIVE){
             throw new CustomException("Your account "+email+" is inactive, please contact admin");
         }

         return org.springframework.security.core.userdetails.User.builder()
                 .username(user.getEmail())
                 .password(user.getPassword())
                 .roles(user.getRole().name())
                 .build();
    }
}
