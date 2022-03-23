package com.salem.budgetApp.services;

import com.salem.budgetApp.enums.ExpensesExceptionErrorMessages;
import com.salem.budgetApp.enums.FilterExpensesParametersEnum;
import com.salem.budgetApp.enums.MonthsEnum;
import com.salem.budgetApp.exceptions.MissingExpensesFilterException;
import com.salem.budgetApp.mappers.ExpensesMapper;
import com.salem.budgetApp.repositories.ExpensesRepository;
import com.salem.budgetApp.repositories.entities.ExpensesEntity;
import com.salem.budgetApp.repositories.entities.UserEntity;
import com.salem.budgetApp.services.dtos.ExpensesDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.*;
import java.util.logging.Filter;
import java.util.stream.Collectors;

@Service
public class ExpensesService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AssetsService.class.getName());

    private final ExpensesRepository expensesRepository;
    private final ExpensesMapper expensesMapper;
    private final UserLogInfoService userLogInfoService;

    public ExpensesService(ExpensesRepository expensesRepository,
                           ExpensesMapper expensesMapper,
                           UserLogInfoService userLogInfoService) {
        this.expensesRepository = expensesRepository;
        this.expensesMapper = expensesMapper;
        this.userLogInfoService = userLogInfoService;
    }


    public void setExpenses(ExpensesDto dto) {
        LOGGER.info("Set Expense");
        LOGGER.debug("ExpensesDto: " + dto);
        UserEntity user = getUserEntity();
        var expenseEntity = expensesMapper.fromDtoToEntity(dto, user);
        expensesRepository.save(expenseEntity);
        LOGGER.info("Expense Saved");
    }

    public List<ExpensesDto> getAllExpenses() {
        LOGGER.debug("Get all expenses");
        UserEntity user = getUserEntity();

        return expensesRepository.getExpenseEntitiesByUser(user)
                .stream().map(entity -> expensesMapper.fromEntityToDto(entity))
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateExpenses(ExpensesDto dto) {
        LOGGER.info("Update Expense");
        LOGGER.debug("ExpenseDto: " + dto);
        var entity = expensesRepository.findById(dto.getId());
        if (entity.isPresent()) {
            updateExpenses(entity.get(), dto);
            LOGGER.info("Expense update");
        }
    }

    private void updateExpenses(ExpensesEntity entity, ExpensesDto dto) {
        if(Objects.nonNull(dto.getPurchaseDate()) && !dto.getPurchaseDate().equals(entity.getPurchaseDate())){
            entity.setPurchaseDate(dto.getPurchaseDate());
        }
        if(Objects.nonNull(dto.getAmount()) && !dto.getAmount().equals(entity.getAmount())){
            entity.setAmount(dto.getAmount());
        }
        if(Objects.nonNull(dto.getCategory()) && !dto.getCategory().equals(entity.getCategory())){
            entity.setCategory(dto.getCategory());
        }
    }


    public void deleteExpenses(ExpensesDto dto){
        LOGGER.info("Delete Expense");
        LOGGER.debug("ExpenseDto: " + dto);
        var user = getUserEntity();
        var entity = expensesMapper.fromDtoToEntity(dto, user);
        expensesRepository.delete(entity);
        LOGGER.info("Expense deleted");
    }

    public List<ExpensesDto> getFilteredExpenses(Map<String, String> filter) {
        if(isFilterForFromToDate(filter)){
            return getAllExpensesBetweenDate(
                    filter.get(FilterExpensesParametersEnum.FROM_DATE.getKey()),
                    filter.get(FilterExpensesParametersEnum.TO_DATE.getKey())
            );
        }else if(isFilterForMonthYear(filter)){
            MonthsEnum month = MonthsEnum.valueOf(filter.get(FilterExpensesParametersEnum.MONTH.getKey()).toUpperCase());
            String year = filter.get(FilterExpensesParametersEnum.YEAR.getKey());
            return getAllExpensesForMonthiInYear(month,year);
        }

        return Collections.emptyList();
    }

    private boolean isFilterForMonthYear(Map<String, String> filter) {
        if(filter.containsKey(FilterExpensesParametersEnum.MONTH.getKey())
                && !filter.containsKey(FilterExpensesParametersEnum.YEAR.getKey())){
            throw new MissingExpensesFilterException(
                    ExpensesExceptionErrorMessages.MISSING_FILTER_KEY.getMessage(),
                    "601D31F212E246F5AFAC5D607EB95312"
            );
        }
        if(filter.containsKey(FilterExpensesParametersEnum.YEAR.getKey())
                && !filter.containsKey(FilterExpensesParametersEnum.MONTH.getKey())){
            throw new MissingExpensesFilterException(
                    ExpensesExceptionErrorMessages.MISSING_FILTER_KEY.getMessage(),
                    "CA7CF6E80FCD434BA132A28CB91C449C"
            );
        }
        return filter.containsKey(FilterExpensesParametersEnum.YEAR.getKey())
                && filter.containsKey(FilterExpensesParametersEnum.MONTH.getKey());
    }

    private boolean isFilterForFromToDate(Map<String, String> filter) {
        if(filter.containsKey(FilterExpensesParametersEnum.FROM_DATE.getKey())
            && !filter.containsKey(FilterExpensesParametersEnum.TO_DATE.getKey())){
            throw new MissingExpensesFilterException(
                    ExpensesExceptionErrorMessages.MISSING_FILTER_KEY.getMessage(),
                    "375C506B154A434F9C8F97155D1F2CA7"
            );
        }
        if(filter.containsKey(FilterExpensesParametersEnum.TO_DATE.getKey())
            && !filter.containsKey(FilterExpensesParametersEnum.FROM_DATE.getKey())){
            throw new MissingExpensesFilterException(
                    ExpensesExceptionErrorMessages.MISSING_FILTER_KEY.getMessage(),
                    "06B1040B400C4B56ADCD649F764B9894"
            );
        }
        return filter.containsKey(FilterExpensesParametersEnum.FROM_DATE.getKey())
                && filter.containsKey(FilterExpensesParametersEnum.TO_DATE.getKey());
    }

    private List<ExpensesDto> getAllExpensesForMonthiInYear(MonthsEnum month, String year) {

        String from = month.getFirstDayForYear(year);
        String to = month.getLastDayForYear(year);

        return getAllExpensesBetweenDate(from, to);
    }

    private List<ExpensesDto> getAllExpensesBetweenDate(String fromDate, String toDate) {
        LOGGER.info("Get All Expenses Between Date");
        LOGGER.debug("fromDate " + fromDate + " toDate " + toDate);
        var user = userLogInfoService.getLoggedUserEntity();
        var dateSuffix = "T00:00:00.001Z";
        var fromInstantDate = Instant.parse(fromDate + dateSuffix);
        var toInstantDate = Instant.parse(toDate + dateSuffix);

        return expensesRepository.findAllByBetweenDate(user, fromInstantDate, toInstantDate)
                .stream().map(entity -> expensesMapper.fromEntityToDto(entity))
                .collect(Collectors.toList());
    }




    private UserEntity getUserEntity() {
        LOGGER.info("getLoggedUserEntity");
        return userLogInfoService.getLoggedUserEntity();
    }
}
