package com.salem.budgetApp.mappers;

import com.salem.budgetApp.repositories.entities.builders.ExpensesDtoBuilder;
import com.salem.budgetApp.repositories.entities.builders.ExpensesEntityBuilder;
import com.salem.budgetApp.repositories.entities.ExpensesEntity;
import com.salem.budgetApp.repositories.entities.UserEntity;
import com.salem.budgetApp.services.dtos.ExpensesDto;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ExpensesMapper {

    public ExpensesEntity fromDtoToEntity(ExpensesDto dto, UserEntity user){
        if(Objects.isNull(dto)){
            return null;
        }

        var entityBuilder = new ExpensesEntityBuilder();

        if(Objects.nonNull(dto.getId())){
            entityBuilder.withId(dto.getId());
        }

        if(Objects.nonNull(dto.getAmount())){
            entityBuilder.withAmount(dto.getAmount());
        }

        if(Objects.nonNull(dto.getPurchaseDate())){
            entityBuilder.withPurchaseDate(dto.getPurchaseDate());
        }

        if(Objects.nonNull(dto.getCategory())){
            entityBuilder.withCategory(dto.getCategory());
        }

        if(Objects.nonNull(user)){
            entityBuilder.withUser(user);
        }

        return entityBuilder.build();
    }

    public ExpensesDto fromEntityToDto(ExpensesEntity entity){

        if(Objects.isNull(entity)){
            return null;
        }

        var dto = new ExpensesDtoBuilder();

        if(Objects.nonNull(entity.getId())){
            dto.withId(entity.getId());
        }

        if(Objects.nonNull(entity.getAmount())){
            dto.withAmount(entity.getAmount());
        }

        if(Objects.nonNull(entity.getPurchaseDate())){
            dto.withPurchaseDate(entity.getPurchaseDate());
        }

        if(Objects.nonNull(entity.getCategory())){
            dto.withCategory(entity.getCategory());
        }

        return dto.build();
    }
}
