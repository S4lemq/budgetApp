package com.salem.budgetApp.validators;
import com.salem.budgetApp.enums.FilterExceptionErrorMessages;
import com.salem.budgetApp.exceptions.MissingAssetsFilterException;
import com.salem.budgetApp.exceptions.MissingExpensesFilterException;
import org.springframework.stereotype.Component;

@Component
public class AssetsFilterParametersValidator extends FilterParametersValidator{

    @Override
    public void throwException(String missingKey, String errorCode) {
        throw new MissingAssetsFilterException(
                FilterExceptionErrorMessages.MISSING_ASSETS_FILTER_KEY.getMessage(missingKey),
                errorCode
        );
    }
}
