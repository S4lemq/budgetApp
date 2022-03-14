package com.salem.budgetApp.exceptions;

import com.salem.budgetApp.enums.AuthenticationMessageEnum;

public class BudgetUserNotFoundException extends RuntimeException{

    public BudgetUserNotFoundException() {
        super(AuthenticationMessageEnum.USER_NOT_FOUND.getMessage());
    }
}
