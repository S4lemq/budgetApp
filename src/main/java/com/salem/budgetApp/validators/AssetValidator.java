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
            throw new AssetIncompleteException(ValidatorsAssetEnum.NO_AMOUNT.getMessage());
        }
    }

}
