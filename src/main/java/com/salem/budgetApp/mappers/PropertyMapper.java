package com.salem.budgetApp.mappers;

import com.salem.budgetApp.builders.PropertyDtoBuilder;
import com.salem.budgetApp.builders.PropertyEntityBuilder;
import com.salem.budgetApp.repositories.entities.PropertyEntity;
import com.salem.budgetApp.repositories.entities.UserEntity;
import com.salem.budgetApp.services.dtos.PropertyDto;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PropertyMapper {

    public PropertyEntity fromDtoToEntity(PropertyDto dto, UserEntity user){
        if(Objects.isNull(dto)){
            return null;
        }

        var entityBuilder = new PropertyEntityBuilder();

        if(Objects.nonNull(dto.getId())){
            entityBuilder.withId(dto.getId());
        }

        if(Objects.nonNull(dto.getRooms())){
            entityBuilder.withRooms(dto.getRooms());
        }

        if(Objects.nonNull(dto.getSingle())){
            entityBuilder.withSingle(dto.getSingle());
        }

        if(Objects.nonNull(dto.getCity())){
            entityBuilder.withCity(dto.getCity());
        }

        if(Objects.nonNull(dto.getPostCode())){
            entityBuilder.withPostCode(dto.getPostCode());
        }

        if(Objects.nonNull(dto.getStreet())){
            entityBuilder.withStreet(dto.getStreet());
        }

        if(Objects.nonNull(dto.getHouse())){
            entityBuilder.withHouse(dto.getHouse());
        }

        if(Objects.nonNull(user)){
            entityBuilder.withUser(user);
        }

        return entityBuilder.build();
    }

    public PropertyDto fromEntityToDto(PropertyEntity entity){
        if(Objects.isNull(entity)){
            return null;
        }

        var dtoBuilder = new PropertyDtoBuilder();

        if(Objects.nonNull(entity.getId())){
            dtoBuilder.withId(entity.getId());
        }

        if(Objects.nonNull(entity.getRooms())){
            dtoBuilder.withRooms(entity.getRooms());
        }

        if(Objects.nonNull(entity.getSingle())){
            dtoBuilder.withSingle(entity.getSingle());
        }

        if(Objects.nonNull(entity.getCity())){
            dtoBuilder.withCity(entity.getCity());
        }

        if(Objects.nonNull(entity.getPostCode())){
            dtoBuilder.withPostCode(entity.getPostCode());
        }

        if(Objects.nonNull(entity.getStreet())){
            dtoBuilder.withStreet(entity.getStreet());
        }

        if(Objects.nonNull(entity.getHouse())){
            dtoBuilder.withHouse(entity.getHouse());
        }

        return dtoBuilder.build();
    }

}
