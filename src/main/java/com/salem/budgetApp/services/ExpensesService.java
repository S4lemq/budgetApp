package com.salem.budgetApp.services;

import com.salem.budgetApp.mappers.ExpensesMapper;
import com.salem.budgetApp.repositories.ExpensesRepository;
import com.salem.budgetApp.repositories.entities.ExpensesEntity;
import com.salem.budgetApp.repositories.entities.UserEntity;
import com.salem.budgetApp.services.dtos.ExpensesDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ExpensesService {

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
        UserEntity user = getUserEntity();
        var expenseEntity = expensesMapper.fromDtoToEntity(dto, user);
        expensesRepository.save(expenseEntity);
    }

    public List<ExpensesDto> getAllExpenses() {
        UserEntity user = getUserEntity();

        return expensesRepository.getExpenseEntitiesByUser(user).stream()
                .map(entity -> expensesMapper.fromEntityToDto(entity))
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateExpenses(ExpensesDto dto) {
        var entity = expensesRepository.findById(dto.getId());
        if (entity.isPresent()) {
            updateExpenses(entity.get(), dto);
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
        var user = getUserEntity();
        var entity = expensesMapper.fromDtoToEntity(dto, user);
        expensesRepository.delete(entity);
    }




    private UserEntity getUserEntity() {
        return userLogInfoService.getLoggedUserEntity();
    }


}
