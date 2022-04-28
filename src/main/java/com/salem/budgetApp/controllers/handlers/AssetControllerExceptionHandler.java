package com.salem.budgetApp.controllers.handlers;

import com.salem.budgetApp.controllers.handlers.dtos.ErrorMessage;
import com.salem.budgetApp.exceptions.AssetIncompleteException;
import com.salem.budgetApp.exceptions.MissingAssetsFilterException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AssetControllerExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorMessage assetIncompleteExceptionHandler(AssetIncompleteException exception){
        return ErrorMessage.ErrorMessageBuilder.anErrorMessage()
                .withErrorCode(exception.getErrorCode())
                .withErrorDescription(exception.getMessage())
                .build();
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE)
    public ErrorMessage missingAssetsFilerExceptionHandler(MissingAssetsFilterException exception){
          return ErrorMessage.ErrorMessageBuilder.anErrorMessage()
                  .withErrorDescription(exception.getMessage())
                  .withErrorCode(exception.getErrorCode())
                  .build();
    }

}
