package com.salem.budgetApp.repositories.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass// info dla hibernate'a że klasa posiada w sobie pola, które będą wykorzysywane również przez klasę encji która ją rozszerza
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseBudgetEntity that = (BaseBudgetEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user);
    }

    @Override
    public String toString() {
        return "BaseBudgetEntity{" +
                "id=" + id +
                ", user=" + user +
                '}';
    }
}
