package com.salem.budgetApp.services.integrations;

import com.salem.budgetApp.builders.ExpensesDtoBuilder;
import com.salem.budgetApp.builders.ExpensesEntityBuilder;
import com.salem.budgetApp.enums.ExpensesCategory;
import com.salem.budgetApp.enums.ExpensesExceptionErrorMessages;
import com.salem.budgetApp.enums.FilterExpensesParametersEnum;
import com.salem.budgetApp.exceptions.MissingExpensesFilterException;
import com.salem.budgetApp.repositories.entities.ExpensesEntity;
import com.salem.budgetApp.services.dtos.ExpensesDto;
import org.assertj.core.util.Streams;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


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

    @Test
    void should_return_all_expenses_saved_in_database_filter_by_date() {
        //given
        var fromDate = "2022-03-01";
        var toDate = "2022-03-21";
        var middleDate = "2022-03-10";
        var notInRangeDate = "2022-03-30";
        var user = initDatabaseByPrimeUser();
        initDatabaseByExpenses(user, fromDate);
        initDatabaseByExpenses(user, toDate);
        initDatabaseByExpenses(user, middleDate);
        initDatabaseByExpenses(user, notInRangeDate);
        Map<String, String> filter = new HashMap<>(){{
            put(FilterExpensesParametersEnum.FROM_DATE.getKey(), fromDate);
            put(FilterExpensesParametersEnum.TO_DATE.getKey(), toDate);
        }};

        //when
        var result = expensesService.getFilteredExpenses(filter);

        //then
        assertThat(result).hasSize(3);
        var dateAsString = result.stream()
                .map(dto -> dto.getPurchaseDate().toString().substring(0,fromDate.length()))
                .collect(Collectors.toList());
        assertThat(dateAsString)
                .contains(fromDate,toDate,middleDate)
                .doesNotContain(notInRangeDate);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource
    void should_throw_exception_when_one_of_the_filters_key(String testName, Map<String, String> filter) {
        //given

        //when
        var result = assertThrows(MissingExpensesFilterException.class,
                () -> expensesService.getFilteredExpenses(filter));

        //then
        assertThat(result.getMessage()).isEqualTo(ExpensesExceptionErrorMessages.MISSING_FILTER_KEY.getMessage());
    }

    private static Stream<Arguments> should_throw_exception_when_one_of_the_filters_key(){
        return Stream.of(
                Arguments.of("test for missing " + FilterExpensesParametersEnum.TO_DATE.getKey(),
                new HashMap<>(){{
                    put(FilterExpensesParametersEnum.TO_DATE.getKey(), "2020-02-20");
                }}),
                Arguments.of("test for missing " + FilterExpensesParametersEnum.FROM_DATE.getKey(),
                new HashMap<>(){{
                    put(FilterExpensesParametersEnum.FROM_DATE.getKey(), "2020-02-20");
                }})
        );
    }
}
