package com.salem.budgetApp.enums;

public enum ExpensesExceptionErrorMessages {
    MISSING_FILTER_KEY("missing filter key: ");

    private final String message;

    ExpensesExceptionErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
