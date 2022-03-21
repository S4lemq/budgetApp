package com.salem.budgetApp.repositories;

import com.salem.budgetApp.repositories.entities.ExpensesEntity;
import com.salem.budgetApp.repositories.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface ExpensesRepository extends JpaRepository<ExpensesEntity, UUID> {

    List<ExpensesEntity> getExpenseEntitiesByUser(UserEntity userEntity);

    @Query("SELECT e FROM ExpensesEntity e WHERE e.user = :user AND e.purchaseDate >= :fromDate AND e.purchaseDate <= :toDate")
    List<ExpensesEntity> findAllByBetweenDate(UserEntity user, Instant fromDate, Instant toDate);
}
