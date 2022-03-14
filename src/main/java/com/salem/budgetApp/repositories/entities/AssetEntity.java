package com.salem.budgetApp.repositories.entities;

import com.salem.budgetApp.enums.AssetCategory;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "assets")
public class AssetEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;
    private BigDecimal amount;
    private Instant incomeDate;
    @Enumerated(EnumType.STRING)
    private AssetCategory category;
    @Transient
    private UserEntity user;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Instant getIncomeDate() {
        return incomeDate;
    }

    public void setIncomeDate(Instant incomeDate) {
        this.incomeDate = incomeDate;
    }

    public AssetCategory getCategory() {
        return category;
    }

    public void setCategory(AssetCategory category) {
        this.category = category;
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
        AssetEntity entity = (AssetEntity) o;
        return Objects.equals(id, entity.id) && Objects.equals(user, entity.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user);
    }

    @Override
    public String toString() {
        return "AssetEntity{" +
                "id=" + id +
                ", amount=" + amount +
                ", incomeDate=" + incomeDate +
                ", category=" + category +
                ", user=" + user +
                '}';
    }
}
