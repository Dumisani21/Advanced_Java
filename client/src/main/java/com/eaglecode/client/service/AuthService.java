package com.eaglecode.client.service;

import com.eaglecode.client.dto.JwtResponse;
import com.eaglecode.client.dto.LoginRequest;
import com.eaglecode.client.dto.RegistrationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class AuthService {
    private final WebClient webClient;

    public AuthService(WebClient webClient) {
        this.webClient = webClient;
    }

    public String login(String username, String password) {
        LoginRequest request = new LoginRequest(username, password);

        try {
            JwtResponse response = webClient.post()
                    .uri("/auth/login")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(JwtResponse.class)
                    .block();

            return response.getToken();
        } catch (Exception e) {
            log.error("Login failed", e);
            throw new RuntimeException("Authentication failed");
        }
    }

    public void register(RegistrationRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        try {
            webClient.post()
                    .uri("/auth/register")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        } catch (Exception e) {
            log.error("Registration failed", e);
            throw new RuntimeException("Registration failed: " + e.getMessage());
        }
    }
}
