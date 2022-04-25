package com.salem.budgetApp.filters;

import com.salem.budgetApp.repositories.ExpensesRepository;
import com.salem.budgetApp.repositories.entities.ExpensesEntity;
import com.salem.budgetApp.repositories.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component("forExpensesRange")
class ExpensesFilterRange extends FilterRangeAbstract<ExpensesEntity>{

    private final ExpensesRepository expensesRepository;

    public ExpensesFilterRange(ExpensesRepository expensesRepository) {
        this.expensesRepository = expensesRepository;
    }

    @Override
    protected List<ExpensesEntity> getAllEntityBetweenDate(UserEntity user, Instant fromDate, Instant toDate) {



        return expensesRepository.findAllByBetweenDate(user, fromDate, toDate);
    }

}
