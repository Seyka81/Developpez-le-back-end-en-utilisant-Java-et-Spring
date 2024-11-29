package com.chatop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.chatop.domain.Rentals;

public interface RentalRepository extends JpaRepository<Rentals, Long> {
}
