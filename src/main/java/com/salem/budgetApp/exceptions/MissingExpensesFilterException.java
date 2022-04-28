package com.salem.budgetApp.exceptions;

public class MissingExpensesFilterException extends BudgetMainException{

    public MissingExpensesFilterException(String message, String errorCode) {
        super(message, errorCode);
    }

}
