package com.chatop.services;

import com.chatop.domain.Rentals;
import com.chatop.model.RentalDTO;
import com.chatop.repositories.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional
@Service
public class RentalServiceImpl implements RentalService {
    @Autowired
    private RentalRepository rentalRepository;

    public List<RentalDTO> findAllRentals() {
        List<Rentals> rentals = rentalRepository.findAll();

        // Convertir les entités Rentals en DTOs
        List<RentalDTO> rentalDTOs = rentals.stream().map(rental -> {
            RentalDTO rentalDto = new RentalDTO();
            rentalDto.setId(rental.getId());
            rentalDto.setName(rental.getName());
            rentalDto.setSurface(rental.getSurface());
            rentalDto.setPrice(rental.getPrice());
            rentalDto.setPicture(rental.getPicture());
            rentalDto.setOwner_id(rental.getOwner_id());
            rentalDto.setDescription(rental.getDescription());
            rentalDto.setCreated_at(rental.getCreated_at().toString());
            rentalDto.setUpdated_at(rental.getUpdated_at().toString());
            return rentalDto;
        }).toList();

        return rentalDTOs;
    }

    public RentalDTO findRentalById(Long id) {
        Rentals rental = rentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental not found"));

        RentalDTO rentalDto = new RentalDTO();
        rentalDto.setId(rental.getId());
        rentalDto.setName(rental.getName());
        rentalDto.setSurface(rental.getSurface());
        rentalDto.setPrice(rental.getPrice());
        rentalDto.setPicture(rental.getPicture());
        rentalDto.setOwner_id(rental.getOwner_id());
        rentalDto.setDescription(rental.getDescription());
        rentalDto.setCreated_at(rental.getCreated_at().toString());
        rentalDto.setUpdated_at(rental.getUpdated_at().toString());

        return rentalDto;

    }

    @Override
    public void saveRental(RentalDTO rentalDto, Long ownerId) {

        Rentals rental = new Rentals();
        rental.setName(rentalDto.getName());
        rental.setSurface(rentalDto.getSurface());
        rental.setPrice(rentalDto.getPrice());
        rental.setDescription(rentalDto.getDescription());
        rental.setPicture(rentalDto.getPicture());
        rental.setCreated_at(LocalDate.now());
        rental.setUpdated_at(LocalDate.now());
        rental.setOwner_id(ownerId);

        rentalRepository.save(rental);
    }

    @Override
    public void updateRental(RentalDTO rentalDto) {
        Rentals rental = rentalRepository.findById(rentalDto.getId())
                .orElseThrow(() -> new RuntimeException("Rental not found"));

        rental.setName(rentalDto.getName());
        rental.setSurface(rentalDto.getSurface());
        rental.setPrice(rentalDto.getPrice());
        rental.setDescription(rentalDto.getDescription());
        rental.setUpdated_at(LocalDate.now());

        rentalRepository.save(rental);
    }

}
