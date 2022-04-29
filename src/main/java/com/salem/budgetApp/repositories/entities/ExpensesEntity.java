package com.salem.budgetApp.repositories.entities;

import com.salem.budgetApp.enums.ExpensesCategory;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "expenses")
@Data
public class ExpensesEntity extends BaseBudgetEntity{

    private BigDecimal amount;
    private Instant purchaseDate;
    @Enumerated(EnumType.STRING)
    private ExpensesCategory category;

}
