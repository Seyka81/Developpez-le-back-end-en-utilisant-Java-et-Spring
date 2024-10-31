package com.chatop.backend.controllers;

import java.util.ArrayList;

import com.chatop.backend.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.backend.domain.User;
import com.chatop.backend.model.UserRegistrationDTO;
import com.chatop.backend.services.UserService;


@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private JwtTokenUtil jwtService;
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;
    /**
     * Méthode d'enregistrement d'un nouvel utilisateur en base de donnée après avoir vérifié que l'email saisi n'existe pas déjà
     * @param registrationDTO
     * @return un statut réponse 201
     */

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegistrationDTO registrationDTO) {
        try {
            ArrayList<User> users = userService.findAllUsers();
            for (User u : users) {
                if (u.getEmail().equals(registrationDTO.getEmail())) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already in use");
                }
            }
            userService.save(registrationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating user");
        }
    }
}