package com.salem.budgetApp.repositories.entities;

import com.salem.budgetApp.enums.ExpensesCategory;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "expenses")
public class ExpensesEntity extends BaseBudgetEntity{

    private BigDecimal amount;
    private Instant purchaseDate;
    @Enumerated(EnumType.STRING)
    private ExpensesCategory category;


    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Instant getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Instant purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public ExpensesCategory getCategory() {
        return category;
    }

    public void setCategory(ExpensesCategory category) {
        this.category = category;
    }


    @Override
    public String toString() {
        return "ExpensesEntity{" +
                "amount=" + amount +
                ", purchaseDate=" + purchaseDate +
                ", category=" + category +
                '}';
    }
}
