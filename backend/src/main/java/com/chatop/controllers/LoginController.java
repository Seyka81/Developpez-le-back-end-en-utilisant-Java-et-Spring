package com.chatop.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.chatop.model.UserLoginDTO;
import com.chatop.model.UserRegistrationDTO;
import com.chatop.security.JwtTokenUtil;
import com.chatop.services.UserService;


@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    /**
     * Méthode d'enregistrement d'un nouvel utilisateur en base de donnée après avoir vérifié que l'email saisi n'existe pas déjà
     * @param registrationDTO
     * @return un statut réponse 201
     */

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationDTO registrationDTO) {
        try {
            return userService.save(registrationDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating user");
        }
    }

    /**
     * Méthode de connexion d'un utilisateur en base de donnée après avoir vérifié que l'email et le mot de passe saisis sont corrects
     * @param userLoginDTO
     * @return un token JWT
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO userLoginDTO) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDTO.getEmail(), userLoginDTO.getPassword()));

            String jwt = jwtTokenUtil.generateToken(userLoginDTO.getEmail());
            Map<String, String> response = new HashMap<>();
            response.put("token", jwt);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error logging in");
        }
    }


}