package com.chatop.backend.repositories;

import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import com.chatop.backend.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByEmail(String name);
    public User findById(long id);
    public ArrayList<User> findAll();
}
