package com.salem.budgetApp.exceptions;

import com.salem.budgetApp.enums.AuthenticationMessageEnum;

public class BudgetInvalidUsernameOrPasswordException extends RuntimeException{

    public BudgetInvalidUsernameOrPasswordException() {
        super(AuthenticationMessageEnum.INVALID_USERNAME_OR_PASSWORD.getMessage());
    }
}
