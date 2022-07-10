package com.salem.budgetApp.repositories.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass// info dla hibernate'a że klasa posiada w sobie pola, które będą wykorzysywane również przez klasę encji która ją rozszerza
@Data//do pól, które wystepują w klasie stworzy automatycznie gettery, settery, toString, hashcode i equals
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BaseBudgetEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;


}
