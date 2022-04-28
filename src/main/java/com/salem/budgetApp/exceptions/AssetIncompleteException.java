package com.salem.budgetApp.exceptions;

public class AssetIncompleteException extends BudgetMainException{

    public AssetIncompleteException(String message, String errorCode) {
        super(message, errorCode);
    }

}
