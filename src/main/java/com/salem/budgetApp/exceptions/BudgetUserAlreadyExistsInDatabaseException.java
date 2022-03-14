package com.salem.budgetApp.exceptions;

import com.salem.budgetApp.enums.AuthenticationMessageEnum;

public class BudgetUserAlreadyExistsInDatabaseException extends RuntimeException{

    public BudgetUserAlreadyExistsInDatabaseException() {
        super(AuthenticationMessageEnum.USER_ALREADY_EXISTS.getMessage());
    }
}
