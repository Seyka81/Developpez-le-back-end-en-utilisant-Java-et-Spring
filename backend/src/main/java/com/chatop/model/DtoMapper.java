package com.chatop.model;

import com.chatop.domain.Rental;
import com.chatop.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DtoMapper {
    DtoMapper INSTANCE = Mappers.getMapper(DtoMapper.class);

    @Mapping(source = "created_at", target = "created_at", dateFormat = "yyyy/MM/dd")
    @Mapping(source = "updated_at", target = "updated_at", dateFormat = "yyyy/MM/dd")
    UserDTO userToUserDto(User user);

    @Mapping(source = "created_at", target = "created_at", dateFormat = "yyyy/MM/dd")
    @Mapping(source = "updated_at", target = "updated_at", dateFormat = "yyyy/MM/dd")
    @Mapping(source = "owner.id", target = "owner_id")
    RentalDto rentalToRentalDto(Rental rental);

    @Mapping(source = "created_at", target = "created_at")
    @Mapping(source = "updated_at", target = "updated_at")
    @Mapping(source = "owner_id", target = "owner.id")
    Rental rentalDtoToRental(RentalDto rentalDto);

}
