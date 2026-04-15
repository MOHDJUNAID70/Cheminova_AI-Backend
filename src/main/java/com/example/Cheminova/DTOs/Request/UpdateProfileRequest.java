package com.example.Cheminova.DTOs.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateProfileRequest {
    private String name;

    @Email(message = "Invalid email format")
    private String email;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number must be between 10 and 15 digits and can start with +")
    private String phone;

    @Size(max = 200, message = "Address cannot exceed 200 characters")
    private String address;

    private Integer age;
}
