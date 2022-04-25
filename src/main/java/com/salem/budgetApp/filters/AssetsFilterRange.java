package com.salem.budgetApp.filters;

import com.salem.budgetApp.repositories.AssetsRepository;
import com.salem.budgetApp.repositories.entities.AssetEntity;
import com.salem.budgetApp.repositories.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component("forAssetsRange")
class AssetsFilterRange extends FilterRangeAbstract<AssetEntity>{

    private final AssetsRepository assetsRepository;

    public AssetsFilterRange(AssetsRepository assetsRepository) {
        this.assetsRepository = assetsRepository;
    }

    @Override
    protected List<AssetEntity> getAllEntityBetweenDate(UserEntity user, Instant fromDate, Instant toDate) {
        return assetsRepository.findAllBetweenDate(user, fromDate, toDate);
    }

}
