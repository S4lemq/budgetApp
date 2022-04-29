package com.salem.budgetApp.repositories.entities;

import com.salem.budgetApp.enums.RoomsType;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "rooms")
@Data
public class RoomsEntity extends BaseBudgetEntity{

    @Enumerated(EnumType.STRING)
    private RoomsType type;
    private BigDecimal cost;

}
