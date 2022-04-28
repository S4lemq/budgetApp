package com.salem.budgetApp.filters;

import com.salem.budgetApp.enums.AssetCategory;
import com.salem.budgetApp.repositories.AssetsRepository;
import com.salem.budgetApp.repositories.entities.AssetEntity;
import com.salem.budgetApp.repositories.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Component("forAssetsRange")
class AssetsFilterRange extends FilterRangeAbstract<AssetEntity>{

    private final AssetsRepository assetsRepository;

    public AssetsFilterRange(AssetsRepository assetsRepository) {
        this.assetsRepository = assetsRepository;
    }

    @Override
    protected List<AssetEntity> getAllEntityBetweenDate(UserEntity user,
                                                        Instant fromDate,
                                                        Instant toDate,
                                                        String category) {
        return assetsRepository.findAllBetweenDate(user, fromDate, toDate, mapStringToEnum(category));
    }

    private List<AssetCategory> mapStringToEnum(String category) {
        if(Objects.isNull(category)){
            return Arrays.asList(AssetCategory.values());
        }
        return Arrays.asList(AssetCategory.valueOf(category.toUpperCase()));
    }

}
