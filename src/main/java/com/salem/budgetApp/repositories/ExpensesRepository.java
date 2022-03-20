package com.salem.budgetApp.repositories;

import com.salem.budgetApp.repositories.entities.ExpensesEntity;
import com.salem.budgetApp.repositories.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExpensesRepository extends JpaRepository<ExpensesEntity, UUID> {

    List<ExpensesEntity> getExpenseEntitiesByUser(UserEntity userEntity);
}
