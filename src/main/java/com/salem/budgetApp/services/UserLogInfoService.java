package com.salem.budgetApp.services;

import com.salem.budgetApp.repositories.UserRepository;
import com.salem.budgetApp.repositories.entities.UserEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserLogInfoService {

    private final UserRepository userRepository;

    public UserLogInfoService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity getLoggedUserEntity(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();//pobranie aktualnego
        //zalogowanego użytkownika dzięki informacjom uzyskanym z kontekstu Spring Security
        var username = authentication.getPrincipal();

        var user = new UserEntity();
        user.setId(UUID.fromString("00000000-0000-0000-0000-000000000000"));

        return user;
    }
}
