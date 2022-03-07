package com.salem.budgetApp.builders;

import com.salem.budgetApp.repositories.entities.AssetEntity;
import com.salem.budgetApp.services.dtos.AssetDto;

import java.math.BigDecimal;
import java.util.UUID;

public class AssetEntityBuilder {
    private UUID id;
    private BigDecimal amount;

    public AssetEntityBuilder withId(UUID id){
        this.id = id;
        return this;
    }

    public AssetEntityBuilder withAmount(BigDecimal amount){
        this.amount = amount;
        return this;
    }

    public AssetEntity build(){
        var dto = new AssetEntity();
        dto.setId(this.id);
        dto.setAmount(this.amount);
        return dto;
    }
}
