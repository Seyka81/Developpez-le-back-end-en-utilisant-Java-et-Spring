package com.chatop.services;

import com.chatop.domain.Users;
import com.chatop.model.UserDTO;
import com.chatop.model.UserRegistrationDTO;
import com.chatop.repositories.UserRepository;
import com.chatop.security.JWTService;

import io.jsonwebtoken.Claims;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JWTService jwtService;

    @Override
    public ResponseEntity<?> save(UserRegistrationDTO registrationDTO) {
        Users user = new Users();
        ArrayList<Users> users = this.findAllUsers();
        for (Users u : users) {
            if (u.getEmail().equals(registrationDTO.getEmail())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already in use");
            }
        }
        user.setEmail(registrationDTO.getEmail());
        user.setName(registrationDTO.getName());
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        user.setCreated_at(LocalDate.now());
        user.setUpdated_at(LocalDate.now());
        userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);

    }

    @Override
    public Optional<Users> findUserById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public Users findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public ArrayList<Users> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<Users> findUserByUsername(String username) {
        return Optional.ofNullable(userRepository.findByEmail(username));
    }

    public UserDTO findUserByToken(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        Claims claims = jwtService.parseToken(token);
        String username = claims.getSubject();
        Optional<Users> userOptional = findUserByUsername(username);

        if (userOptional.isPresent()) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(userOptional.get().getId());
            userDTO.setName(userOptional.get().getName());
            userDTO.setEmail(userOptional.get().getEmail());
            userDTO.setCreated_at(userOptional.get().getCreated_at().toString());
            userDTO.setUpdated_at(userOptional.get().getUpdated_at().toString());

            return userDTO;
        } else {
            throw new RuntimeException("User not found");
        }
    }

}
