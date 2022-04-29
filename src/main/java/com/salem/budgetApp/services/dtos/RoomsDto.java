package com.salem.budgetApp.services.dtos;

import com.salem.budgetApp.enums.RoomsType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class RoomsDto {
    private UUID id;
    private RoomsType type;
    private BigDecimal cost;

}
