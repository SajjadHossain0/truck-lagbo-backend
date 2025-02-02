package com.truck_lagbo_backend.Authentication.Services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.truck_lagbo_backend.Authentication.Entities.User;
import com.truck_lagbo_backend.Authentication.Repositories.UserRepo;
import com.truck_lagbo_backend.Components.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserDataService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private EmailService emailService;

    public String register(User user) {
        user.setEmail(user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        userRepo.save(user);
        return "User registered successfully";
    }

    public String login(String email, String password) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        return jwtTokenUtil.generateToken(user);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public void changePassword(Long userId, String oldPassword, String newPassword) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("Invalid old password");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);
    }

    public String generateResetToken(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Generate a unique token
        String resetToken = UUID.randomUUID().toString();
        user.setResetToken(resetToken);
        user.setTokenExpiryDate(LocalDateTime.now().plusMinutes(15)); // Token valid for 15 minutes
        userRepo.save(user);

        return resetToken;
    }

    public void sendResetEmail(String email, String resetToken) {
        String resetLink = "http://localhost:3000/reset-password?token=" + resetToken; // Frontend reset link
        String message = "Click the link to reset your password : " + resetLink;

        emailService.sendEmail(email, "Password Reset Request", message);
    }

    public void resetPassword(String token, String newPassword) {
        User user = userRepo.findByResetToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid or expired token"));

        if (user.getTokenExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Reset token has expired");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        user.setTokenExpiryDate(null);
        userRepo.save(user);
    }

    public String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip; // Convert localhost IPv6 to IPv4
    }

    public String getLocationFromIp(String ip) {
        try {
            if (ip.equals("127.0.0.1")) {
                return "Localhost";
            }

            String url = "http://ip-api.com/json/" + ip;
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response);

            String country = jsonNode.has("country") ? jsonNode.get("country").asText() : "Unknown";
            String city = jsonNode.has("city") ? jsonNode.get("city").asText() : "Unknown";

            return country + ", " + city;
        } catch (Exception e) {
            return "Unknown";
        }
    }


}
