package com.salem.budgetApp.services.integrations;

import com.salem.budgetApp.builders.ExpensesDtoBuilder;
import com.salem.budgetApp.builders.ExpensesEntityBuilder;
import com.salem.budgetApp.enums.ExpensesCategory;
import com.salem.budgetApp.mappers.ExpensesMapper;
import com.salem.budgetApp.repositories.ExpensesRepository;
import com.salem.budgetApp.repositories.UserRepository;
import com.salem.budgetApp.repositories.entities.ExpensesEntity;
import com.salem.budgetApp.repositories.entities.UserEntity;
import com.salem.budgetApp.services.ExpensesService;
import com.salem.budgetApp.services.dtos.ExpensesDto;
import org.assertj.core.util.Streams;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@WithMockUser(username = "user123", password = "123user")
public class ExpensesIntegrationTest {

    @Autowired
    private ExpensesService service;
    @Autowired
    private ExpensesRepository expensesRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ExpensesMapper expensesMapper;


    @Test
    void should_save_one_expenses_in_to_database(){
        //given
        initDefaultMockUserInDatabase();
        ExpensesDto dto = new ExpensesDtoBuilder()
                .withAmount(BigDecimal.ONE)
                .withCategory(ExpensesCategory.FOR_LIFE)
                .withPurchaseDate(Instant.now())
                .build();

        //when
        service.setExpenses(dto);

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
        initDatabaseByDefaultMockUserAndHisAssets();
        initDatabaseBySecondMockUserAndHisAssets();

        //when
        var result = service.getAllExpenses();

        //then
        assertThat(result).hasSize(3);
    }

    @Test
    void should_update_expenses_in_database(){
        //given
        var newAmount = new BigDecimal(100);
        var newCategory = ExpensesCategory.EDUCATION;
        var newPurchaseDate = Instant.now();
        initDatabaseByDefaultMockUserAndHisAssets();
        var entityId = expensesRepository.findAll().get(0).getId();
        ExpensesEntity changedEntity = new ExpensesEntityBuilder()
                .withId(entityId)
                .withAmount(newAmount)
                .withCategory(newCategory)
                .withPurchaseDate(newPurchaseDate)
                .withUser(expensesRepository.findById(entityId).get().getUser())
                .build();

        //when
        service.updateExpenses(expensesMapper.fromEntityToDto(changedEntity));

        //then
        assertThat(expensesRepository.findById(entityId).get().getAmount()).isEqualTo(newAmount);
        assertThat(expensesRepository.findById(entityId).get().getCategory()).isEqualTo(newCategory);
        assertThat(expensesRepository.findById(entityId).get().getPurchaseDate()).isEqualTo(newPurchaseDate);
    }

    @Test
    void should_delete_expenses_from_database() {
        //given
        initDatabaseByDefaultMockUserAndHisAssets();
        int numberOfLeaveExpenses = 2;

        var dataBaseBeforeDelete = expensesRepository.findAll();
        var entityIdToDelete = expensesRepository.findAll().get(0).getId();
        var entityToDelete = expensesRepository.findById(entityIdToDelete).get();

        //when
        service.deleteExpenses(expensesMapper.fromEntityToDto(entityToDelete));

        //then
        assertThat(expensesRepository.findAll()).hasSize(numberOfLeaveExpenses);
        var DBWithoutDeletedExpenses = Streams.stream(dataBaseBeforeDelete)
                .filter(entity -> !entity.equals(entityToDelete))
                .collect(Collectors.toList());
        assertThat(expensesRepository.findAll()).isEqualTo(DBWithoutDeletedExpenses);
    }

    private UserEntity initDefaultMockUserInDatabase(){
        var user = new UserEntity();
        user.setUsername("user123");
        user.setPassword("123user");
        return userRepository.save(user);
    }

    private UserEntity initSecondMockUserInDatabase(){
        var user = new UserEntity();
        user.setUsername("secondUser123");
        user.setPassword("123secondUser");
        return userRepository.save(user);
    }

    private void initDatabaseByDefaultMockUserAndHisAssets() {
        var user = initDefaultMockUserInDatabase();

        ExpensesEntity expense1 = new ExpensesEntityBuilder()
                .withAmount(BigDecimal.ONE)
                .withCategory(ExpensesCategory.FOR_LIFE)
                .withPurchaseDate(Instant.now())
                .withUser(user)
                .build();

        ExpensesEntity expense2 = new ExpensesEntityBuilder()
                .withAmount(new BigDecimal(20))
                .withCategory(ExpensesCategory.EDUCATION)
                .withPurchaseDate(Instant.now())
                .withUser(user)
                .build();

        ExpensesEntity expense3 = new ExpensesEntityBuilder()
                .withAmount(new BigDecimal(50))
                .withCategory(ExpensesCategory.FUN)
                .withPurchaseDate(Instant.now())
                .withUser(user)
                .build();

        expensesRepository.saveAll(asList(expense1, expense2, expense3));
    }

    private void initDatabaseBySecondMockUserAndHisAssets() {
        var user = initSecondMockUserInDatabase();

        ExpensesEntity expense1 = new ExpensesEntityBuilder()
                .withAmount(BigDecimal.ONE)
                .withCategory(ExpensesCategory.FOR_LIFE)
                .withPurchaseDate(Instant.now())
                .withUser(user)
                .build();

        ExpensesEntity expense2 = new ExpensesEntityBuilder()
                .withAmount(new BigDecimal(20))
                .withCategory(ExpensesCategory.EDUCATION)
                .withPurchaseDate(Instant.now())
                .withUser(user)
                .build();

        ExpensesEntity expense3 = new ExpensesEntityBuilder()
                .withAmount(new BigDecimal(50))
                .withCategory(ExpensesCategory.FUN)
                .withPurchaseDate(Instant.now())
                .withUser(user)
                .build();

        expensesRepository.saveAll(asList(expense1, expense2, expense3));
    }

}
