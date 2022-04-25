package com.salem.budgetApp.validators.filter;

import com.salem.budgetApp.enums.FilterExceptionErrorMessages;
import com.salem.budgetApp.exceptions.MissingExpensesFilterException;
import org.springframework.stereotype.Component;

@Component("forExpensesValidator")
class ExpensesFilterParametersValidator extends FilterParametersValidator {

    @Override
    public void throwException(String missingKey, String errorCode) {
        throw new MissingExpensesFilterException(
                FilterExceptionErrorMessages.MISSING_EXPENSES_FILTER_KEY.getMessage(missingKey),
                errorCode
        );

        //            throw new MissingExpensesFilterException(
//                    getMessageToException(FilterExpensesParametersEnum.MONTH.getKey()),
//                    "CA7CF6E80FCD434BA132A28CB91C449C"
//            );
    }
}
