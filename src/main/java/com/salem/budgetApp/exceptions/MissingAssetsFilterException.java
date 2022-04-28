package com.salem.budgetApp.exceptions;

public class MissingAssetsFilterException extends BudgetMainException{

    public MissingAssetsFilterException(String message, String errorCode) {
        super(message, errorCode);
    }

}
