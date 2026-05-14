package com.example.Cheminova.DTOs.Response;

import com.example.Cheminova.Enum.Role;
import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private Role role;
}
