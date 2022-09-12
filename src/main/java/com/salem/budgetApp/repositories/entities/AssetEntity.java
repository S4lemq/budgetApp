package com.salem.budgetApp.repositories.entities;

import com.salem.budgetApp.enums.AssetCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "assets")
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AssetEntity extends BaseBudgetEntity{

    private BigDecimal amount;
    private Instant incomeDate;
    @Enumerated(EnumType.STRING)
    private AssetCategory category;
    private String description;

}
