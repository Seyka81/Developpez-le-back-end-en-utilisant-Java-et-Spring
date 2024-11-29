package com.chatop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chatop.model.MessagesDTO;
import com.chatop.services.MessagesService;

@Controller
@RequestMapping("/api")
public class MessagesController {

    @Autowired
    private MessagesService messagesService;

    /**
     * Méthode de création d'un message attaché à une maison de location
     * 
     * @param token
     * @param messageDto
     * @return un statut réponse 201
     */
    @PostMapping("/messages")
    public ResponseEntity<String> createMessage(@RequestHeader("Authorization") String token,
            @RequestBody MessagesDTO messagesDTO) {
        try {
            messagesService.saveMessage(messagesDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body("Message created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating message");
        }
    }

}
