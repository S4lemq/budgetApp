package com.salem.budgetApp.mappers;

import com.salem.budgetApp.builders.AssetDtoBuilder;
import com.salem.budgetApp.builders.AssetEntityBuilder;
import com.salem.budgetApp.repositories.entities.AssetEntity;
import com.salem.budgetApp.repositories.entities.UserEntity;
import com.salem.budgetApp.services.dtos.AssetDto;
import org.springframework.objenesis.Objenesis;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AssetsMapper {

    public AssetEntity fromDtoToEntity(AssetDto dto, UserEntity user){
        if(Objects.isNull(dto)){
            return null;
        }
        var entityBuilder = new AssetEntityBuilder();

        if(Objects.nonNull(dto.getId())){
            entityBuilder.withId(dto.getId());
        }

        if(Objects.nonNull(dto.getAmount())){
            entityBuilder.withAmount(dto.getAmount());
        }

        if(Objects.nonNull(dto.getIncomeDate())){
            entityBuilder.withInsuranceDate(dto.getIncomeDate());
        }

        if(Objects.nonNull(dto.getCategory())){
            entityBuilder.withCategory(dto.getCategory());
        }

        if(Objects.nonNull(user)){
            entityBuilder.withUser(user);
        }

        return entityBuilder.build();
    }

    public AssetDto fromEntityToDto(AssetEntity entity){
        if(Objects.isNull(entity)){
            return null;
        }

        var dtoBuilder = new AssetDtoBuilder();

        if(Objects.nonNull(entity.getId())){
            dtoBuilder.withId(entity.getId());
        }

        if(Objects.nonNull(entity.getAmount())){
            dtoBuilder.withAmount(entity.getAmount());
        }

        if(Objects.nonNull(entity.getIncomeDate())){
            dtoBuilder.withIncomeDate(entity.getIncomeDate());
        }

        if(Objects.nonNull(entity.getCategory())){
            dtoBuilder.withCategory(entity.getCategory());
        }

        return dtoBuilder.build();
    }
}

