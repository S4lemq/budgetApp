package com.salem.budgetApp.services;

import com.salem.budgetApp.services.dtos.AuthenticationJwtToken;
import com.salem.budgetApp.services.dtos.AuthenticationUserDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserDetailsService userDetailsService;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserDetailsServiceImpl userDetailsService, JWTService jwtService, AuthenticationManager authenticationManager) {
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationJwtToken createAuthenticationToken(AuthenticationUserDto authenticationUserDto){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationUserDto.getUserName(), authenticationUserDto.getPassword()
        ));
        var userDetails = userDetailsService.loadUserByUsername(authenticationUserDto.getUserName());
        var jwtToken = jwtService.generateJWTToken(userDetails);

        return new AuthenticationJwtToken(jwtToken);
    }
}
