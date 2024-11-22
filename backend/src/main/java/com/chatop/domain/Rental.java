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
public class Rental implements Serializable {

    private String name;

    private float surface;

    private double price;

    private String picture;

    @Column(length = 1000)
    private String description;

    @ManyToOne
    private User owner;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDate created_at;

    private LocalDate updated_at;
}
