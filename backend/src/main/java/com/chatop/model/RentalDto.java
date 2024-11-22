package com.chatop.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class RentalDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private float surface;

    private double price;

    private String picture;

    private String description;

    private Long owner_id;

    private String created_at;

    private String updated_at;

}
