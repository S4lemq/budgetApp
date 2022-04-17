package com.salem.budgetApp.exceptions;

public class MissingExpensesFilterException extends RuntimeException{

    private String errorCode;

    public MissingExpensesFilterException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
