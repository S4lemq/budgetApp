package com.salem.budgetApp.validators;

import com.salem.budgetApp.enums.FilterExceptionErrorMessages;
import com.salem.budgetApp.exceptions.MissingExpensesFilterException;
import org.springframework.stereotype.Component;

@Component
public class ExpensesFilterParametersValidator extends FilterParametersValidator{

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
