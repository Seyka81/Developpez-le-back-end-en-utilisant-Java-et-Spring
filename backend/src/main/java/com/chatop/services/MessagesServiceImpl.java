package com.chatop.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chatop.domain.Messages;
import com.chatop.model.MessagesDTO;
import com.chatop.repositories.MessagesRepository;

@Transactional
@Service
public class MessagesServiceImpl implements MessagesService {

    @Autowired
    private MessagesRepository messageRepository;

    @Override
    public void saveMessage(MessagesDTO messageDto) {
        Messages message = new Messages();
        message.setMessage(messageDto.getMessage());
        message.setRental_id(messageDto.getRental_id());
        message.setUser_id(messageDto.getUser_id());
        message.setCreated_at(LocalDate.now());
        message.setUpdated_at(LocalDate.now());
        messageRepository.save(message);
    }

}
