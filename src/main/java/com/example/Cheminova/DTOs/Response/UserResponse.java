package com.example.Cheminova.DTOs.Response;

import lombok.Data;


@Data
public class UserResponse {
    private String name;
    private String email;
    private String phone;
    private String address;
    private Integer age;
}
