package com.salem.budgetApp.services;

import com.salem.budgetApp.exceptions.BudgetUserNotFoundException;
import com.salem.budgetApp.repositories.UserRepository;
import com.salem.budgetApp.repositories.entities.UserEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
public class UserLogInfoService {

    private final UserRepository userRepository;

    public UserLogInfoService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity getLoggedUserEntity(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();//pobranie aktualnego
        //zalogowanego użytkownika dzięki informacjom uzyskanym z kontekstu Spring Security
        var username = ((User)authentication.getPrincipal()).getUsername();

        return userRepository.findByUsername(username)
                .orElseThrow(BudgetUserNotFoundException::new);
    }
}
