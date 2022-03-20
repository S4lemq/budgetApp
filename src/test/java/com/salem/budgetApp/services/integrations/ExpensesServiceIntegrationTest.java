package com.salem.budgetApp.services.integrations;

import com.salem.budgetApp.builders.ExpensesDtoBuilder;
import com.salem.budgetApp.builders.ExpensesEntityBuilder;
import com.salem.budgetApp.enums.ExpensesCategory;
import com.salem.budgetApp.repositories.entities.ExpensesEntity;
import com.salem.budgetApp.services.dtos.ExpensesDto;
import org.assertj.core.util.Streams;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


public class ExpensesServiceIntegrationTest extends InitIntegrationTestData{

    @Test
    void should_save_one_expenses_in_to_database(){
        //given
        initDatabaseByPrimeUser();
        ExpensesDto dto = new ExpensesDtoBuilder()
                .withAmount(BigDecimal.ONE)
                .withCategory(ExpensesCategory.FOR_LIFE)
                .withPurchaseDate(Instant.now())
                .build();

        //when
        expensesService.setExpenses(dto);

        //then
        var allExpensesInEntity = expensesRepository.findAll();
        assertThat(allExpensesInEntity).hasSize(1);
        assertThat(allExpensesInEntity.get(0).getAmount()).isEqualTo(dto.getAmount());
        assertThat(allExpensesInEntity.get(0).getCategory()).isEqualTo(dto.getCategory());
        assertThat(allExpensesInEntity.get(0).getPurchaseDate()).isEqualTo(dto.getPurchaseDate());
    }

    @Test
    void should_return_all_expenses_saved_in_database() {
        //given
        initDatabaseByDefaultMockUserAndHisExpenses();
        initDatabaseBySecondMockUserAndHisExpenses();
        //when
        var result = expensesService.getAllExpenses();

        //then
        assertThat(result).hasSize(3);
    }

    @Test
    void should_update_expenses_in_database(){
        //given
        var newAmount = new BigDecimal(100);
        var newCategory = ExpensesCategory.EDUCATION;
        var newPurchaseDate = Instant.now();
        initDatabaseByDefaultMockUserAndHisExpenses();
        var entityId = expensesRepository.findAll().get(0).getId();
        ExpensesEntity changedEntity = new ExpensesEntityBuilder()
                .withId(entityId)
                .withAmount(newAmount)
                .withCategory(newCategory)
                .withPurchaseDate(newPurchaseDate)
                .withUser(expensesRepository.findById(entityId).get().getUser())
                .build();

        //when
        expensesService.updateExpenses(expensesMapper.fromEntityToDto(changedEntity));

        //then
        assertThat(expensesRepository.findById(entityId).get().getAmount()).isEqualTo(newAmount);
        assertThat(expensesRepository.findById(entityId).get().getCategory()).isEqualTo(newCategory);
        assertThat(expensesRepository.findById(entityId).get().getPurchaseDate()).isEqualTo(newPurchaseDate);
    }

    @Test
    void should_delete_expenses_from_database() {
        //given
        initDatabaseByDefaultMockUserAndHisExpenses();
        int numberOfLeaveExpenses = 2;

        var dataBaseBeforeDelete = expensesRepository.findAll();
        var entityIdToDelete = expensesRepository.findAll().get(0).getId();
        var entityToDelete = expensesRepository.findById(entityIdToDelete).get();

        //when
        expensesService.deleteExpenses(expensesMapper.fromEntityToDto(entityToDelete));

        //then
        assertThat(expensesRepository.findAll()).hasSize(numberOfLeaveExpenses);
        var DBWithoutDeletedExpenses = Streams.stream(dataBaseBeforeDelete)
                .filter(entity -> !entity.equals(entityToDelete))
                .collect(Collectors.toList());
        assertThat(expensesRepository.findAll()).isEqualTo(DBWithoutDeletedExpenses);
    }
}
