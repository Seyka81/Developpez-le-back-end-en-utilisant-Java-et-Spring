package com.chatop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.model.DtoMapper;
import com.chatop.model.UserDTO;
import com.chatop.model.UserLoginDTO;
import com.chatop.model.UserRegistrationDTO;
import com.chatop.security.JWTService;
import com.chatop.services.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    /**
     * Méthode de login
     * 
     * @param loginData
     * @return un token autorisant les requêtes vers l'API et un statut réponse 200
     */
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO userLoginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginDTO.getEmail(), userLoginDTO.getPassword()));
            String token = jwtService.generateToken(authentication);
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AuthenticationException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid login credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }

    /**
     * Méthode d'enregistrement d'un nouvel utilisateur en base de donnée après
     * avoir vérifié que l'email saisi n'existe pas déjà
     * 
     * @param user
     * @return un statut réponse 201
     */

    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationDTO registrationDTO) {
        userService.save(registrationDTO);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(registrationDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating user");
        }
    }

    /**
     * Méthode permettant de récupérer les informations d'un utilisateur à partir de
     * son token
     * 
     * @param token
     * @return les informations de l'utilisateur
     */
    @GetMapping("/auth/me")
    public ResponseEntity<UserDTO> getUserByToken(@RequestHeader("Authorization") String token) {
        try {
            UserDTO UserDTO = userService.findUserByToken(token);
            return ResponseEntity.status(HttpStatus.OK).body(UserDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Méthode de récupération d'un User par son id
     * 
     * @param id
     * @return un statut réponse 200
     */
    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        return userService.findUserById(id)
                .map(user -> {
                    UserDTO userDto = DtoMapper.INSTANCE.userToUserDto(user);
                    return ResponseEntity.status(HttpStatus.OK).body(userDto);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

}
