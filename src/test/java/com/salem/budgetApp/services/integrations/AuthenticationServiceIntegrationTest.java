package com.salem.budgetApp.services.integrations;

import com.salem.budgetApp.enums.AuthenticationMessageEnum;
import com.salem.budgetApp.exceptions.BudgetInvalidUsernameOrPasswordException;
import com.salem.budgetApp.services.AuthenticationService;
import com.salem.budgetApp.services.dtos.UserDetailsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class AuthenticationServiceIntegrationTest  extends InitIntegrationTestData{

    private AuthenticationService authenticationService;

    @BeforeEach
    public void setup(){
        authenticationService = new AuthenticationService(
          userDetailsService,
          jwtService,
          authenticationManager);
    }

    @Test
    void should_throw_an_BudgetInvalidUsernameOrPasswordException_when_username_is_incorrect(){
        //given
        initDatabaseByPrimeUser();

        UserDetailsDto dto = new UserDetailsDto();
        dto.setUsername("incorrectUserName");
        dto.setPassword(USER_PASSWORD_PRIME);

        //when
        var result = assertThrows(BudgetInvalidUsernameOrPasswordException.class,
                () -> authenticationService.createAuthenticationToken(dto));

        //then
        assertThat(result).isNotNull();
        assertThat(result.getMessage()).isEqualTo(AuthenticationMessageEnum.INVALID_USERNAME_OR_PASSWORD.getMessage());
    }

    @Test
    void should_throw_an_BudgetInvalidUsernameOrPasswordException_when_password_is_incorrect(){
        //given
        initDatabaseByPrimeUser();

        UserDetailsDto dto = new UserDetailsDto();
        dto.setUsername(USER_NAME_PRIME);
        dto.setPassword("incorrectPassword");

        //when
        var result = assertThrows(BudgetInvalidUsernameOrPasswordException.class,
                () -> authenticationService.createAuthenticationToken(dto));

        //then
        assertThat(result).isNotNull();
        assertThat(result.getMessage()).isEqualTo(AuthenticationMessageEnum.INVALID_USERNAME_OR_PASSWORD.getMessage());
    }


}
