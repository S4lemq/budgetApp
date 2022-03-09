package com.salem.budgetApp.builders;

import com.salem.budgetApp.repositories.entities.AssetEntity;
import com.salem.budgetApp.services.dtos.AssetDto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class AssetEntityBuilder {
    private UUID id;
    private BigDecimal amount;
    private Instant incomeDate;

    public AssetEntityBuilder withId(UUID id){
        this.id = id;
        return this;
    }

    public AssetEntityBuilder withAmount(BigDecimal amount){
        this.amount = amount;
        return this;
    }

    public AssetEntityBuilder withInsuranceDate(Instant incomeDate){
        this.incomeDate = incomeDate;
        return this;
    }

    public AssetEntity build(){
        var entity = new AssetEntity();
        entity.setId(this.id);
        entity.setAmount(this.amount);
        entity.setIncomeDate(this.incomeDate);
        return entity;
    }
}