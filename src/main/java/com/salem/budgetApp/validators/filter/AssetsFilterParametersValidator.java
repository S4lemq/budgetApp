package com.salem.budgetApp.validators.filter;

import com.salem.budgetApp.enums.FilterExceptionErrorMessages;
import com.salem.budgetApp.exceptions.MissingAssetsFilterException;
import org.springframework.stereotype.Component;

@Component("forAssetsValidator")
class AssetsFilterParametersValidator extends FilterParametersValidator {

    @Override
    public void throwException(String missingKey, String errorCode) {
        throw new MissingAssetsFilterException(
                FilterExceptionErrorMessages.MISSING_ASSETS_FILTER_KEY.getMessage(missingKey),
                errorCode
        );
    }
}
