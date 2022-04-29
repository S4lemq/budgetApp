package com.salem.budgetApp.repositories.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "property")
@Data
public class PropertyEntity extends BaseBudgetEntity{

    private Integer rooms;
    private Boolean single;
    private String city;
    private String postCode;
    private String street;
    private String house;

}
