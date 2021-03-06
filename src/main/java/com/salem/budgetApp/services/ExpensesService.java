package com.salem.budgetApp.services;

import com.salem.budgetApp.enums.FilterSpecification;
import com.salem.budgetApp.filters.FilterRangeStrategy;
import com.salem.budgetApp.mappers.ExpensesMapper;
import com.salem.budgetApp.repositories.ExpensesRepository;
import com.salem.budgetApp.repositories.entities.ExpensesEntity;
import com.salem.budgetApp.repositories.entities.UserEntity;
import com.salem.budgetApp.services.dtos.ExpensesDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class
ExpensesService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AssetsService.class.getName());

    private final ExpensesRepository expensesRepository;
    private final ExpensesMapper expensesMapper;
    private final UserLogInfoService userLogInfoService;
    private final FilterRangeStrategy<ExpensesEntity> filterRangeStrategy;

    public ExpensesService(ExpensesRepository expensesRepository,
                           ExpensesMapper expensesMapper,
                           UserLogInfoService userLogInfoService,
                           FilterRangeStrategy filterRangeStrategy) {
        this.expensesRepository = expensesRepository;
        this.expensesMapper = expensesMapper;
        this.userLogInfoService = userLogInfoService;
        this.filterRangeStrategy = filterRangeStrategy;
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
        var user = userLogInfoService.getLoggedUserEntity();
        FilterSpecification specification = FilterSpecification.FOR_EXPENSES;

        return filterRangeStrategy.getFilteredDataForSpecification(filter, specification, user)
                .stream()
                .map(expensesMapper::fromEntityToDto)
                .collect(Collectors.toList());
    }

    private UserEntity getUserEntity() {
        LOGGER.info("getLoggedUserEntity");
        return userLogInfoService.getLoggedUserEntity();
    }
}
