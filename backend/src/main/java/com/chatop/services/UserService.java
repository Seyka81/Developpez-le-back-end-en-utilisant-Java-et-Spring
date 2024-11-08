package com.chatop.services;


import java.util.ArrayList;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.chatop.domain.Users;
import com.chatop.model.UserRegistrationDTO;

public interface UserService {

    ResponseEntity<?> save(UserRegistrationDTO registrationDTO);
    Optional<Users> findUserById(long id);

    Users findUserByEmail(String email);

    ArrayList<Users> findAllUsers();

}
