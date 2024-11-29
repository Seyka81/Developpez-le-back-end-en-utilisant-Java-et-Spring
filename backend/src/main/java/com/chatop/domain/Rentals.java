package com.chatop.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rentals implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 255)
    private String name;

    private float surface;

    private double price;

    @Column(length = 255)
    private String picture;

    @Column(length = 2000)
    private String description;

    private long owner_id;

    private LocalDate created_at;

    private LocalDate updated_at;
}
