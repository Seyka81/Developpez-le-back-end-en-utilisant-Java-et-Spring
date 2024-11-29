package com.chatop.services;

import com.chatop.model.RentalDTO;

import java.util.List;

public interface RentalService {

    List<RentalDTO> findAllRentals();

    RentalDTO findRentalById(Long id);

    void saveRental(RentalDTO rentalDto, Long ownerId);

    void updateRental(RentalDTO rental);
}
