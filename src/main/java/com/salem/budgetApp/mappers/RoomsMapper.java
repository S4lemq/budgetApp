package com.salem.budgetApp.mappers;

import com.salem.budgetApp.repositories.entities.RoomsEntity;
import com.salem.budgetApp.repositories.entities.UserEntity;
import com.salem.budgetApp.services.dtos.RoomsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoomsMapper {

    @Mapping(source = "dto.id", target = "id")
    RoomsEntity fromDtoToEntity(RoomsDto dto, UserEntity user);

}
