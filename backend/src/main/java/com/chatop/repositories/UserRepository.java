package com.chatop.repositories;

import java.util.ArrayList;
import org.springframework.lang.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import com.chatop.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByEmail(String email);
    @NonNull
    public ArrayList<User> findAll();
}
