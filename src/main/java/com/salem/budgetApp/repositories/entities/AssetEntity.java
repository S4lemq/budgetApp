package com.salem.budgetApp.repositories.entities;

import com.salem.budgetApp.enums.AssetCategory;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "assets")
public class AssetEntity extends BaseBudgetEntity{

    private BigDecimal amount;
    private Instant incomeDate;
    @Enumerated(EnumType.STRING)
    private AssetCategory category;
    private String description;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Instant getIncomeDate() {
        return incomeDate;
    }

    public void setIncomeDate(Instant incomeDate) {
        this.incomeDate = incomeDate;
    }

    public AssetCategory getCategory() {
        return category;
    }

    public void setCategory(AssetCategory category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "AssetEntity{" +
                "amount=" + amount +
                ", incomeDate=" + incomeDate +
                ", category=" + category +
                ", description='" + description + '\'' +
                '}';
    }
}
