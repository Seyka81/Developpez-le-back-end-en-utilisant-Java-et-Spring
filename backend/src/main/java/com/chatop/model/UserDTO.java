package com.chatop.model;

import lombok.Data;

@Data
public class UserDTO {

    protected long id;

    private String name;

    private String email;

    private String created_at;

    private String updated_at;

}
