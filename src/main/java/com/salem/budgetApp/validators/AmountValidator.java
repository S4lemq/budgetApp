package com.salem.budgetApp.validators;

import com.salem.budgetApp.enums.ValidatorsAssetEnum;
import com.salem.budgetApp.services.dtos.AssetDto;

import java.util.Objects;

public class AmountValidator implements Validator{

    private Validator next = new IncomeDateValidator();

    @Override
    public ValidatorMessage valid(AssetDto dto, ValidatorMessage validatorMessage) {
        if(Objects.isNull(dto.getAmount())){
            validatorMessage.setMessage(ValidatorsAssetEnum.NO_AMOUNT.getMessage());
            validatorMessage.setCode("E06122F8BE16439AAA22F38CF4073BC9");
        }

        return next.valid(dto, validatorMessage);
    }
}
