package com.example.Cheminova.Service;

import com.example.Cheminova.DTOs.Request.LoginRequest;
import com.example.Cheminova.DTOs.Request.RegisterRequest;
import com.example.Cheminova.DTOs.Response.LoginResponse;
import com.example.Cheminova.Enum.UserStatus;
import com.example.Cheminova.Exception.CustomException;
import com.example.Cheminova.JWT.JwtService;
import com.example.Cheminova.Mapper.UserMapper;
import com.example.Cheminova.Model.Users;
import com.example.Cheminova.OTP.otpGenerator;
import com.example.Cheminova.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private otpGenerator otpGenerator;

    @Autowired
    private EmailService emailService;

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

        String otp= otpGenerator.generateOtp();

        Users user = new Users();
        user.setName(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setAge(request.getAge());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        user.setStatus(UserStatus.ACTIVE);
        user.setOtp(otp);
        user.setOtpExpiration(LocalDateTime.now().plusMinutes(10));
        user.setVerified(false);
        userRepository.save(user);

        // Send OTP email
        emailService.sendOtpMail(user.getEmail(), otp);

    }

    // VERIFY OTP
    public ResponseEntity<?> verifyOtp(String email, String otp) {

        Users user = userRepository.findByEmail(email);

        // check if already verified
        if (user.isVerified()) {
            return ResponseEntity.badRequest().body("Email already verified");
        }

        // check OTP expiry
        if (LocalDateTime.now().isAfter(user.getOtpExpiration())
        ) {
            return ResponseEntity.badRequest().body("OTP has expired. Please request a new one.");
        }

        // check OTP match
        if (!user.getOtp().equals(otp)) {
            return ResponseEntity.badRequest().body("Invalid OTP");
        }

        // mark as verified
        user.setVerified(true);
        user.setOtp(null);           // clear OTP
        user.setOtpExpiration(null);     // clear expiry
        userRepository.save(user);

        emailService.sendSuccessMail(email);

        return ResponseEntity.ok("Email verified successfully! You can now login.");
    }

    // RESEND OTP
    public ResponseEntity<?> resendOtp(String email) {

        Users user = userRepository.findByEmail(email);

        if (user.isVerified()) {
            return ResponseEntity.badRequest().body("Email already verified");
        }

        // generate new OTP
        String newOtp = otpGenerator.generateOtp();
        user.setOtp(newOtp);
        user.setOtpExpiration(LocalDateTime.now().plusMinutes(10));
        userRepository.save(user);

        emailService.sendOtpMail(email, newOtp);

        return ResponseEntity.ok("New OTP sent to your email.");
    }

    @Transactional
    public LoginResponse verifyUser(LoginRequest request) {
        Users user=userRepository.findByEmail(request.getEmail());
        if(user==null){
            throw new CustomException("Email Doesn't Exist");
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword())
        );

        if(!user.isVerified()){
            throw new CustomException("Please verify your email first.");
        }

        String token=jwtService.generateKey(user.getEmail());
        LoginResponse response=new LoginResponse();
        response.setToken(token);
        response.setRole(user.getRole());
        return response;
    }
}
