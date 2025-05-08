package com.DM.dairyManagement.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.DM.dairyManagement.model.User;
import com.DM.dairyManagement.repository.UserRepository;

import java.util.Optional;
import java.util.Random;

@Controller
public class ForgotPasswordController {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private String generatedCode;
    private String userEmail;

    // Show forgot password form
    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot_password";
    }

    // Send verification code
    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email, Model model) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            model.addAttribute("error", "No user found with this email.");
            return "forgot_password";
        }

        userEmail = email;
        generatedCode = generateCode();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset Code");
        message.setText("Your verification code is: " + generatedCode);

        try {
            mailSender.send(message);
            return "redirect:/verify-code";
        } catch (Exception e) {
            e.printStackTrace(); // Logs actual error in console
            model.addAttribute("error", "Failed to send email. Try again.");
            return "forgot_password";
        }

    }

    // Show verify code form
    @GetMapping("/verify-code")
    public String showVerifyCodeForm() {
        return "verify_code";
    }

    // Process code verification
    @PostMapping("/verify-code")
    public String verifyCode(@RequestParam("code") String code, Model model) {
        if (generatedCode != null && generatedCode.equals(code)) {
            return "redirect:/reset-password";
        } else {
            model.addAttribute("error", "Invalid verification code.");
            return "verify_code";
        }
    }

    // Show reset password form
    @GetMapping("/reset-password")
    public String showResetPasswordForm() {
        return "reset_password_form";
    }

    // Reset password
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam("password") String password,
                                @RequestParam("confirmPassword") String confirmPassword,
                                Model model) {
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match.");
            return "reset_password_form";
        }

        Optional<User> userOpt = userRepository.findByEmail(userEmail);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
            model.addAttribute("message", "Password reset successful. You can now login.");
            return "redirect:/login";
        } else {
            model.addAttribute("error", "Something went wrong. User not found.");
            return "reset_password_form";
        }
    }

    // Utility: generate 6-digit code
    private String generateCode() {
        Random random = new Random();
        return String.valueOf(100000 + random.nextInt(900000));
    }
}
