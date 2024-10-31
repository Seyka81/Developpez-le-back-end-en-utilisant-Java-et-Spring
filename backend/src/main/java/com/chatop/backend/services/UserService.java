package com.chatop.backend.services;


import java.util.ArrayList;
import java.util.Optional;

import com.chatop.backend.domain.User;
import com.chatop.backend.model.UserRegistrationDTO;

public interface UserService {

    void save(UserRegistrationDTO registrationDTO);
    Optional<User> findUserById(long id);

    Optional<User> findUserByUsername(String username);

    ArrayList<User> findAllUsers();

}
