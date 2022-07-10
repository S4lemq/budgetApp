package com.salem.budgetApp.mappers;

import com.salem.budgetApp.repositories.entities.RoomsEntity;
import com.salem.budgetApp.repositories.entities.UserEntity;
import com.salem.budgetApp.services.dtos.RoomsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import javax.persistence.MappedSuperclass;

@Mapper(componentModel = "spring")
public interface RoomsMapper {

    @Mapping(source = "dto.id", target = "id")
    RoomsEntity fromDtoToEntity(RoomsDto dto, UserEntity user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    RoomsEntity fromDtoToEntity(RoomsDto dto,
                                @MappingTarget /*mówi mapstructowi, ze to jest obiekt który będzie zmodyfikowany*/
                                RoomsEntity entity);


    RoomsDto fromEntityToDto(RoomsEntity room);
}
