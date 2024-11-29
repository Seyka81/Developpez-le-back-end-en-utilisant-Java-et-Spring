package com.chatop.model;

import lombok.Data;

@Data
public class MessagesDTO {

    protected int user_id;

    private int rental_id;

    private String message;

}
