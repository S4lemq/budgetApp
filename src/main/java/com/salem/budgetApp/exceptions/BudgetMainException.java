package com.salem.budgetApp.exceptions;

public class BudgetMainException extends RuntimeException{

    private String errorCode;

    public BudgetMainException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

}
