package com.chatop.services;


import java.util.ArrayList;
import java.util.Optional;
import com.chatop.model.UserDTO;

import org.springframework.http.ResponseEntity;

import com.chatop.domain.User;
import com.chatop.model.UserRegistrationDTO;

public interface UserService {

    ResponseEntity<?> save(UserRegistrationDTO registrationDTO);

    Optional<User> findUserById(long id);

    User findUserByEmail(String email);

    ArrayList<User> findAllUsers();

    Optional<User> findUserByUsername(String username);

    UserDTO findUserByToken(String token);
}
