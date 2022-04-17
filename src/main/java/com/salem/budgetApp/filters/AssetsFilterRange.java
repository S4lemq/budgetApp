package com.salem.budgetApp.filters;

import com.salem.budgetApp.repositories.AssetsRepository;
import com.salem.budgetApp.repositories.entities.AssetEntity;
import com.salem.budgetApp.repositories.entities.ExpensesEntity;
import com.salem.budgetApp.repositories.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class AssetsFilterRange extends FilterRangeAbstract{

    private final AssetsRepository assetsRepository;

    public AssetsFilterRange(AssetsRepository assetsRepository) {
        this.assetsRepository = assetsRepository;
    }

    @Override
    protected List<AssetEntity> getAllEntityBetweenDate(UserEntity user, Instant fromDate, Instant toDate) {
        return assetsRepository.findAllBetweenDate(user, fromDate, toDate);
    }

    @Override
    protected String getFilterName() {
        return "AssetsFilter";
    }
}
