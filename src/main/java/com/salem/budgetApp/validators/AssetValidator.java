package com.salem.budgetApp.validators;

import com.salem.budgetApp.exceptions.AssetIncompleteException;
import com.salem.budgetApp.services.dtos.AssetDto;
import org.springframework.stereotype.Component;

@Component
public class AssetValidator {

    private Validator validator = new AmountValidator();

    public void validate(AssetDto dto){
        var validatorMessage = validator.valid(dto, new ValidatorMessage());

        if(validatorMessage.getMessage().isEmpty()){
            return;
        }

        throw new AssetIncompleteException(validatorMessage.getMessage(), validatorMessage.getCode());
    }
}
