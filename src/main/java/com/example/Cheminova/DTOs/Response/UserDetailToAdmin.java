package com.example.Cheminova.DTOs.Response;


import com.example.Cheminova.Enum.Role;
import com.example.Cheminova.Enum.UserStatus;
import lombok.Data;

@Data
public class UserDetailToAdmin {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private Integer age;
    private Role role;
    private UserStatus status;
}
