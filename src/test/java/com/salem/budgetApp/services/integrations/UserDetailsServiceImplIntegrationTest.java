package com.salem.budgetApp.services.integrations;


import com.salem.budgetApp.enums.AuthenticationMessageEnum;
import com.salem.budgetApp.exceptions.BudgetUserAlreadyExistsInDatabaseException;
import com.salem.budgetApp.exceptions.BudgetUserNotFoundException;
import com.salem.budgetApp.services.dtos.UserDetailsDto;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserDetailsServiceImplIntegrationTest extends InitIntegrationTestData{

    @Test
    void should_return_user_with_userName_and_password_from_database(){
        //given
        initDatabaseByPrimeUser();

        //when
        var result = userDetailsService.loadUserByUsername(USER_NAME_PRIME);

        //then
        assertThat(result.getUsername()).isEqualTo(USER_NAME_PRIME);
        assertThat(result.getPassword()).isEqualTo(USER_PASSWORD_PRIME);
    }

    @Test
    void should_save_user_in_to_database(){
        //given
        UserDetailsDto dto = new UserDetailsDto();
        dto.setUsername(USER_NAME_PRIME);
        dto.setPassword(USER_PASSWORD_PRIME);
        var bCryptPrefix = "$2a$10$";
        String bCryptRegex = "^[$]2[abxy]?[$](?:0[4-9]|[12][0-9]|3[01])[$][./0-9a-zA-Z]{53}$";

        //when
        UUID userId = userDetailsService.saveUser(dto);

        //then
        assertThat(userId).isNotNull();
        var userEntityOptional = userRepository.findByUsername(USER_NAME_PRIME);
        var userEntity = userEntityOptional.get();
        assertAll(
                () -> assertThat(userEntity.getUsername()).isEqualTo(USER_NAME_PRIME),
                () -> assertThat(userEntity.getPassword()).contains(bCryptPrefix),
                () -> assertThat(userEntity.getPassword()).matches(Pattern.compile(bCryptRegex))
        );
    }

    @Test
    void should_throw_exception_when_user_is_not_found_in_database(){
        //given
        initDatabaseByPrimeUser();

        //when
        var result = assertThrows(BudgetUserNotFoundException.class,
                () -> userDetailsService.loadUserByUsername("wrongUser"));

        //then
        assertThat(result.getMessage()).isEqualTo(AuthenticationMessageEnum.USER_NOT_FOUND.getMessage());
    }

    @Test
    void should_throw_exception_when_user_already_exists_in_database(){
        //given
        initDatabaseByPrimeUser();
        UserDetailsDto dto = new UserDetailsDto();
        dto.setUsername(USER_NAME_PRIME);
        dto.setPassword(USER_PASSWORD_PRIME);

        //when
        var result = assertThrows(BudgetUserAlreadyExistsInDatabaseException.class,
                () -> userDetailsService.saveUser(dto));

        //then
        assertThat(result.getMessage()).isEqualTo(AuthenticationMessageEnum.USER_ALREADY_EXISTS.getMessage());
    }

    @Test
    void should_remove_user_which_do_not_have_any_assets_in_database(){
        //given
        initDatabaseByPrimeUser();

        var userInDatabase = userRepository.findAll();
        assertThat(userInDatabase).hasSize(1);

        //when
        userDetailsService.deleteUser();

        //then
        var userInDatabaseAfterRemove = userRepository.findAll();
        assertThat(userInDatabaseAfterRemove).hasSize(0);
    }

    @Test
    void should_remove_user_which_has_one_asset_in_database(){
        //given
        initDatabaseByPrimeUser();
        var userEntity = userRepository.findByUsername(USER_NAME_PRIME).get();
        initDatabaseByAssetsForUser(userEntity);

        var userInDatabase = userRepository.findAll();
        assertThat(userInDatabase).hasSize(1);
        var assetsInDatabase = assetRepository.findAll();
        assertThat(assetsInDatabase).hasSize(1);
        assertThat(assetsInDatabase.get(0).getUser()).isEqualTo(userEntity);

        //when
        userDetailsService.deleteUser();

        //then
        var userInDatabaseAfterDelete = userRepository.findAll();
        assertThat(userInDatabaseAfterDelete).hasSize(0);
        var assetsInDatabaseAfterDelete = assetRepository.findAll();
        assertThat(assetsInDatabaseAfterDelete).hasSize(0);

    }

}
