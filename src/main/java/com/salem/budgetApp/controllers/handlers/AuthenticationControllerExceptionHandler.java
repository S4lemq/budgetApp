package com.salem.budgetApp.controllers.handlers;

import com.salem.budgetApp.controllers.handlers.dtos.ErrorMessage;
import com.salem.budgetApp.exceptions.BudgetInvalidUsernameOrPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthenticationControllerExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorMessage usernameOrPasswordIncorrectExceptionHandler(BudgetInvalidUsernameOrPasswordException exception){
        return ErrorMessage.ErrorMessageBuilder.anErrorMessage()
                .withErrorDescription(exception.getMessage())
                .withErrorCode(String.valueOf(HttpStatus.FORBIDDEN.value()))
                .build();
    }
}
