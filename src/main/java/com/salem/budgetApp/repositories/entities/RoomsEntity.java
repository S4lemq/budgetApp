package com.salem.budgetApp.repositories.entities;

import com.salem.budgetApp.enums.RoomsType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "rooms")
public class RoomsEntity extends BaseBudgetEntity{

    @Enumerated(EnumType.STRING)
    private RoomsType type;
    private BigDecimal cost;

    public RoomsType getType() {
        return type;
    }

    public void setType(RoomsType type) {
        this.type = type;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "RoomsEntity{" +
                "type=" + type +
                ", cost=" + cost +
                '}';
    }
}
