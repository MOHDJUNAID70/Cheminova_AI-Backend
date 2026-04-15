package com.example.Cheminova.DTOs.Request;

import com.example.Cheminova.Enum.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterRequest {

    @NotNull(message = "Name cannot be null")
    @Column(nullable = false)
    private String name;

    @Email(message = "Invalid email format")
    @Column(nullable = false,  unique = true)
    private String email;

    @NotNull(message = "Password cannot be null")
    @Column(nullable = false)
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
             message = "Password must be at least 8 characters long and include at least one uppercase letter, " +
                     "one lowercase letter, one number, and one special character")
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @NotNull
    @Column(nullable = false)
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number must be between 10 and 15 digits and can start with +")
    private String phone;

    @NotBlank
    @Size(max=200, message = "Address cannot exceed 200 characters")
    private String address;

    @NotNull
    @Column(nullable = false)
    private Integer age;
}
