package com.salem.budgetApp.controllers.handlers;

import com.salem.budgetApp.controllers.handlers.dtos.ErrorMessage;
import com.salem.budgetApp.exceptions.MissingExpensesFilterException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ExpensesControllerExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE)
    public ErrorMessage missingExpensesFilterExceptionHandler(MissingExpensesFilterException exception){
        return ErrorMessage.ErrorMessageBuilder.anErrorMessage()
                .withErrorCode(exception.getErrorCode())
                .withErrorDescription(exception.getMessage())
                .build();
    }

}
