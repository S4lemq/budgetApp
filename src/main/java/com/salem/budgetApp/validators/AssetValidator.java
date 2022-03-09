package com.salem.budgetApp.validators;

import com.salem.budgetApp.enums.ValidatorsAssetEnum;
import com.salem.budgetApp.exceptions.AssetIncompleteException;
import com.salem.budgetApp.services.dtos.AssetDto;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AssetValidator {

    public void validate(AssetDto dto){
        if(Objects.isNull(dto.getAmount())){
            throw new AssetIncompleteException(ValidatorsAssetEnum.NO_AMOUNT.getMessage(), "E06122F8BE16439AAA22F38CF4073BC9");
        }
        if(Objects.isNull(dto.getIncomeDate())){
            throw new AssetIncompleteException(ValidatorsAssetEnum.NO_INCOME_DATE.getMessage(), "BEC14159418E4C5CA69FCC757EFCC637");
        }
    }

}
