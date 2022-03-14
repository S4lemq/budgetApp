package com.salem.budgetApp.controllers;

import com.salem.budgetApp.services.AuthenticationService;
import com.salem.budgetApp.services.UserDetailsServiceImpl;
import com.salem.budgetApp.services.dtos.AuthenticationJwtToken;
import com.salem.budgetApp.services.dtos.UserDetailsDto;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserDetailsServiceImpl userDetailsService;

    public AuthenticationController(AuthenticationService authenticationService, UserDetailsServiceImpl userDetailsService) {
        this.authenticationService = authenticationService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping
    public AuthenticationJwtToken getAuthenticationToken(@RequestBody UserDetailsDto userDetailsDto){
        return authenticationService.createAuthenticationToken(userDetailsDto);
    }

    @PostMapping
    public UUID setUserDetails(@RequestBody UserDetailsDto userDetailsDto){
        return userDetailsService.saveUser(userDetailsDto);
    }


}
