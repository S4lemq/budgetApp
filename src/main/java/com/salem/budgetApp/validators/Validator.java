package com.salem.budgetApp.validators;

import com.salem.budgetApp.services.dtos.AssetDto;

public interface Validator {

    ValidatorMessage valid(AssetDto dto, ValidatorMessage validatorMessage);

}
