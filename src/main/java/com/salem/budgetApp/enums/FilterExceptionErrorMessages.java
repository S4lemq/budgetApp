package com.salem.budgetApp.enums;

public enum FilterExceptionErrorMessages {
    MISSING_EXPENSES_FILTER_KEY("Missing filter key for expenses: "),
    MISSING_ASSETS_FILTER_KEY("Missing filter key for assets: ");

    private final String message;

    FilterExceptionErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage(String missingKey) {
        return this.message + missingKey;
    }

}
