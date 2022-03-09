package com.salem.budgetApp.validators;

import com.salem.budgetApp.enums.ValidatorsAssetEnum;
import com.salem.budgetApp.services.dtos.AssetDto;

import java.util.Objects;

class IncomeDateValidator implements Validator{
    @Override
    public ValidatorMessage valid(AssetDto dto, ValidatorMessage validatorMessage) {
        if(Objects.isNull(dto.getIncomeDate())){
            validatorMessage.setMessage(ValidatorsAssetEnum.NO_INCOME_DATE.getMessage());
            validatorMessage.setCode("BEC14159418E4C5CA69FCC757EFCC637");
        }

        return validatorMessage;
    }
}
