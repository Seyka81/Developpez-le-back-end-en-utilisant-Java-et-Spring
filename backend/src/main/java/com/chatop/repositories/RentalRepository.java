package com.chatop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.chatop.domain.Rental;

public interface RentalRepository extends JpaRepository<Rental, Long> {
}
