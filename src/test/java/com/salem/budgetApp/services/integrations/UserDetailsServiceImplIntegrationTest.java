package com.salem.budgetApp.services.integrations;


import com.salem.budgetApp.enums.AuthenticationMessageEnum;
import com.salem.budgetApp.exceptions.BudgetUserAlreadyExistsInDatabaseException;
import com.salem.budgetApp.exceptions.BudgetUserNotFoundException;
import com.salem.budgetApp.repositories.UserRepository;
import com.salem.budgetApp.repositories.entities.UserEntity;
import com.salem.budgetApp.services.UserDetailsServiceImpl;
import com.salem.budgetApp.services.dtos.UserDetailsDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.UUID;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class UserDetailsServiceImplIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    private static final String USER_NAME = "userName";
    private static final String USER_PASSWORD = "userPassword";

    @Test
    void should_return_user_with_userName_and_password_from_database(){
        //given
        init_database();

        //when
        var result = userDetailsService.loadUserByUsername(USER_NAME);

        //then
        assertThat(result.getUsername()).isEqualTo(USER_NAME);
        assertThat(result.getPassword()).isEqualTo(USER_PASSWORD);
    }

    @Test
    void should_save_user_in_to_database(){
        //given
        UserDetailsDto dto = new UserDetailsDto();
        dto.setUsername(USER_NAME);
        dto.setPassword(USER_PASSWORD);
        var bCryptPrefix = "$2a$10$";
        String bCryptRegex = "^[$]2[abxy]?[$](?:0[4-9]|[12][0-9]|3[01])[$][./0-9a-zA-Z]{53}$";

        //when
        UUID userId = userDetailsService.saveUser(dto);

        //then
        assertThat(userId).isNotNull();
        var userEntityOptional = userRepository.findByUsername(USER_NAME);
        var userEntity = userEntityOptional.get();
        assertAll(
                () -> assertThat(userEntity.getUsername()).isEqualTo(USER_NAME),
                () -> assertThat(userEntity.getPassword()).contains(bCryptPrefix),
                () -> assertThat(userEntity.getPassword()).matches(Pattern.compile(bCryptRegex))
        );
    }

    @Test
    void should_throw_exception_when_user_is_not_found_in_database(){
        //given
        init_database();

        //when
        var result = assertThrows(BudgetUserNotFoundException.class,
                () -> userDetailsService.loadUserByUsername("wrongUser"));

        //then
        assertThat(result.getMessage()).isEqualTo(AuthenticationMessageEnum.USER_NOT_FOUND.getMessage());
    }

    @Test
    void should_throw_exception_when_user_already_exists_in_database(){
        //given
        init_database();
        UserDetailsDto dto = new UserDetailsDto();
        dto.setUsername(USER_NAME);
        dto.setPassword(USER_PASSWORD);

        //when
        var result = assertThrows(BudgetUserAlreadyExistsInDatabaseException.class,
                () -> userDetailsService.saveUser(dto));

        //then
        assertThat(result.getMessage()).isEqualTo(AuthenticationMessageEnum.USER_ALREADY_EXISTS.getMessage());
    }

    private void init_database(){
        UserEntity entity = new UserEntity();
        entity.setUsername(USER_NAME);
        entity.setPassword(USER_PASSWORD);
        userRepository.save(entity);
    }

}
