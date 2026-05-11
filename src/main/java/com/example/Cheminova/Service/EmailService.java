package com.example.Cheminova.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendOtpMail(String toEmail, String otp){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("CheminNova - Email Verification OTP");
        message.setText(
                "Hello!\n\n" +
                        "Your OTP for email verification is: " + otp + "\n\n" +
                        "This OTP is valid for 10 minutes only.\n\n" +
                        "If you did not request this, please ignore this email.\n\n" +
                        "Best Regards\n\n" +
                        "Team CheminNova"
        );
        mailSender.send(message);
    }

    public void sendSuccessMail(String toEmail){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("CheminNova - Email Verification Success");
        message.setText(
                        "Your email has been successfully verified. You can now log in and explore the features of our application.\n\n" +
                        "Thank You for being a part of CheminNova.\n\n"+
                        "If you have any questions or need assistance, feel free to contact our support team.\n\n" +
                        "Best Regards\n\n" +
                        "Team CheminNova"
        );
        mailSender.send(message);
    }
}
