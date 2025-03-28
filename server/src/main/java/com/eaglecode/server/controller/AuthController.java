package com.eaglecode.server.controller;

import com.eaglecode.server.dto.JwtAuthenticationResponse;
import com.eaglecode.server.dto.LoginRequest;
import com.eaglecode.server.dto.RegistrationRequest;
import com.eaglecode.server.model.Role;
import com.eaglecode.server.model.User;
import com.eaglecode.server.security.JwtTokenProvider;
import com.eaglecode.server.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("http://localhost:8081")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService service;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequest registrationRequest) {
        User user = new User();
        user.setUsername(registrationRequest.getUsername());
        user.setPassword(registrationRequest.getPassword());
        user.setRole(Role.valueOf(registrationRequest.getRole()));

        return ResponseEntity.ok(service.createUser(user));

    }

//    @PostMapping("/refresh")
//    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
//
//        String token = request.getHeader("Authorization").substring(7);
//        if (tokenProvider.validateToken(token)) {
//            String username = tokenProvider.getUsernameFromToken(token);
//            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//            UsernamePasswordAuthenticationToken authentication =
//                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//            String newToken = tokenProvider.generateToken(authentication);
//            return ResponseEntity.ok(new JwtAuthenticationResponse(newToken));
//        }
//        return ResponseEntity.badRequest().body("Invalid token");
//    }

}
