package com.chatop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatop.domain.Messages;

public interface MessagesRepository extends JpaRepository<Messages, Long> {
}
