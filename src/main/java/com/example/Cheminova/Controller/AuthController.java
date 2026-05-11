package com.example.Cheminova.Controller;

import com.example.Cheminova.DTOs.Request.LoginRequest;
import com.example.Cheminova.DTOs.Request.RegisterRequest;
import com.example.Cheminova.JWT.JwtService;
import com.example.Cheminova.Service.AuthService;
import com.example.Cheminova.Service.TokenBlacklistService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private TokenBlacklistService blacklistService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequest request) {
        authService.register(request);
        return new ResponseEntity<>("Register successfully, Please check your email for OTP Verification.", HttpStatus.CREATED);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody Map<String, String> body) {
        return authService.verifyOtp(body.get("email"), body.get("otp"));
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<?> resendOtp(@RequestBody Map<String, String> body) {
        return authService.resendOtp(body.get("email"));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(authService.verifyUser(request));
    }

    @DeleteMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {

        if(token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Invalid token");
        }

        String username = token.substring(7);

        long ExpirationTime=jwtService.getExpiration(username);

        blacklistService.blacklistToken(username, ExpirationTime);

        return ResponseEntity.ok("Logout successfully");
    }

}
