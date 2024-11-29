package com.chatop.model;

import lombok.Data;

@Data
public class RentalDTO {

    private long id;

    private String name;

    private float surface;

    private double price;

    private String picture;

    private String description;

    private long owner_id;

    private String created_at;

    private String updated_at;

}
