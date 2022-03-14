package com.salem.budgetApp.mappers;

import com.salem.budgetApp.repositories.entities.UserEntity;
import com.salem.budgetApp.services.dtos.UserDetailsDto;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;


@Component
public class UserMapper {

    public UserEntity fromDtoToEntity(UserDetailsDto userDetailsDto){
        var entity = new UserEntity();

        entity.setPassword(encodePassword(userDetailsDto.getPassword()));
        entity.setUsername(userDetailsDto.getUsername());

        return entity;
    }

    private String encodePassword(String password) {
        var salt = BCrypt.gensalt();
        return BCrypt.hashpw(password,salt);
    }

}
