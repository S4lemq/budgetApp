package com.salem.budgetApp.services;


import com.salem.budgetApp.enums.AuthenticationMessageEnum;
import com.salem.budgetApp.exceptions.BudgetInvalidUsernameOrPasswordException;
import com.salem.budgetApp.services.dtos.UserDetailsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private UserDetailsServiceImpl userDetailsService;
    private AuthenticationService authenticationService;

    private static final String USERNAME = "userName";
    private static final String USERPASSWORD = "userPassword";

    @BeforeEach
    public void setup(){
        var jwtService = new JWTService();
        authenticationService = new AuthenticationService(
                userDetailsService,
                jwtService,
                authenticationManager);
    }

    @Test
    void should_return_token_when_user_and_password_match(){
        //given
        String expectedTokenHeader = "eyJhbGciOiJIUzI1NiJ9";

        UserDetailsDto authenticationUser = new UserDetailsDto();
        authenticationUser.setUsername(USERNAME);
        authenticationUser.setPassword(USERPASSWORD);

        Collection authorities = Collections.emptyList();
        UserDetails userDetails = new User(USERNAME, USERPASSWORD, authorities);

        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(USERNAME, USERPASSWORD);
        when(authenticationManager.authenticate(authenticationToken)).thenReturn(authenticationToken);
        when(userDetailsService.loadUserByUsername(USERNAME)).thenReturn(userDetails);

        //when
        var result = authenticationService.createAuthenticationToken(authenticationUser);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getJwtToken()).isNotNull();
        assertThat(result.getJwtToken().substring(0,20)).isEqualTo(expectedTokenHeader);
    }

    @Test
    void should_throw_an_BudgetInvalidUsernameOrPasswordException_when_username_is_incorrect(){
        //given
        UserDetailsDto authenticationUser = new UserDetailsDto();
        authenticationUser.setUsername(USERNAME);
        authenticationUser.setPassword(USERPASSWORD);

        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(USERNAME,USERPASSWORD);
        when(authenticationManager.authenticate(authenticationToken)).thenThrow(BadCredentialsException.class);

        //when
        var result = assertThrows(BudgetInvalidUsernameOrPasswordException.class,
                () -> authenticationService.createAuthenticationToken(authenticationUser));

        //then
        assertThat(result).isNotNull();
        assertThat(result.getMessage()).isEqualTo(AuthenticationMessageEnum.INVALID_USERNAME_OR_PASSWORD.getMessage());
    }

}
