package com.salem.budgetApp.services;

import com.salem.budgetApp.services.dtos.AuthenticationJwtToken;
import com.salem.budgetApp.services.dtos.AuthenticationUserDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserDetailsService userDetailsService;
    private final JWTService jwtService;

    public AuthenticationService(UserDetailsService userDetailsService, JWTService jwtService) {
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    public AuthenticationJwtToken createAuthenticationToken(AuthenticationUserDto authenticationUserDto){
        var userDetails = userDetailsService.loadUserByUsername(authenticationUserDto.getUserName());
        var jwtToken = jwtService.generateJWTToken(userDetails);

        return new AuthenticationJwtToken(jwtToken);
    }
}
