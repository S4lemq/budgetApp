package com.salem.budgetApp.repositories.entities;

import com.salem.budgetApp.enums.RoomsType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "rooms")
@Data
@SuperBuilder// przez ograniczenia lomboka i wykorzystanie pól klasy abstrakcyjnej
@AllArgsConstructor
@NoArgsConstructor
public class RoomsEntity extends BaseBudgetEntity{

    @Enumerated(EnumType.STRING)
    private RoomsType type;
    private BigDecimal cost;
    @Transient
    private Boolean rent;
}
