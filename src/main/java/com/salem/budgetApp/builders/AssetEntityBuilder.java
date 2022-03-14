package com.salem.budgetApp.builders;

import com.salem.budgetApp.enums.AssetCategory;
import com.salem.budgetApp.repositories.entities.AssetEntity;
import com.salem.budgetApp.repositories.entities.UserEntity;
import com.salem.budgetApp.services.dtos.AssetDto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class AssetEntityBuilder {
    private UUID id;
    private BigDecimal amount;
    private Instant incomeDate;
    private AssetCategory category;
    private UserEntity user;

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

    public AssetEntityBuilder withCategory(AssetCategory category){
        this.category = category;
        return this;
    }

    public AssetEntityBuilder withUser(UserEntity user) {
        this.user = user;
        return this;
    }

    public AssetEntity build(){
        var entity = new AssetEntity();
        entity.setId(this.id);
        entity.setAmount(this.amount);
        entity.setIncomeDate(this.incomeDate);
        entity.setCategory(this.category);
        entity.setUser(this.user);
        return entity;
    }


}
