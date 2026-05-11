package com.example.Cheminova.OTP;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class otpGenerator {

    public String generateOtp(){
        Random rand = new Random();
        int otp=100000 + rand.nextInt(900000);
        return String.valueOf(otp);
    }
}
