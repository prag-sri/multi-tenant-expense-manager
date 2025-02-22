package com.expense.management.controllers;

import com.expense.management.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService= authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody Map<String,Object> request) {
        String username = (String) request.get("username");
        String password = (String) request.get("password");
        String email = (String) request.get("email");
        List<String> roles = (List<String>) request.get("roles");
        String company = (String) request.get("company");

        String token = authService.registerUser(username, password, email, roles, company);

        // Create response
        Map<String, String> response = new HashMap<>();
        response.put("message", "User registered successfully");
        response.put("token", token);

        // Return ResponseEntity with success status and response body
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String,String> request) {
        String username = (String) request.get("username");
        String password = (String) request.get("password");

        String token = authService.authenticateUser(username, password);

        // Create response
        Map<String, String> response = new HashMap<>();
        response.put("message", "Login successful");
        response.put("token", token);

        // Return ResponseEntity with success status and response body
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
