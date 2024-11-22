package com.chatop.services;

import com.chatop.domain.Rental;
import com.chatop.domain.User;
import com.chatop.model.DtoMapper;
import com.chatop.model.RentalDto;
import com.chatop.repositories.RentalRepository;
import com.chatop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class RentalServiceImpl implements RentalService {
    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private UserRepository userRepository;

    public List<RentalDto> findAllRentals() {
        List<Rental> rentals = rentalRepository.findAll();
        return rentals.stream()
                .map(DtoMapper.INSTANCE::rentalToRentalDto)
                .collect(Collectors.toList());
    }

    public RentalDto findRentalById(Long id) {
        Optional<Rental> rental = rentalRepository.findById(id);
        return rental.map(DtoMapper.INSTANCE::rentalToRentalDto)
                .orElseThrow(() -> new RuntimeException("Rental not found"));
    }

    @Override
    public void saveOrUpdateRental(RentalDto rentalDto, Long ownerId) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        Rental rental = DtoMapper.INSTANCE.rentalDtoToRental(rentalDto);
        rental.setOwner(owner);

        Rental savedRental = rentalRepository.save(rental);
        DtoMapper.INSTANCE.rentalToRentalDto(savedRental);
    }

    @Override
    public void updateRental(RentalDto rentalDto) {
        Rental rental = rentalRepository.findById(rentalDto.getId())
                .orElseThrow(() -> new RuntimeException("Rental not found"));

        rental.setName(rentalDto.getName());
        rental.setSurface(rentalDto.getSurface());
        rental.setPrice(rentalDto.getPrice());
        rental.setDescription(rentalDto.getDescription());
        rental.setUpdated_at(LocalDate.now());

        rentalRepository.save(rental);
    }

}
