package com.chatop.services;

import com.chatop.model.RentalDto;

import java.util.List;

public interface RentalService {

    List<RentalDto> findAllRentals();

    RentalDto findRentalById(Long id);

    void saveOrUpdateRental(RentalDto rentalDto, Long ownerId);

    void updateRental(RentalDto rental);
}
