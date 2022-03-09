package com.salem.budgetApp.services.integrations;

import com.salem.budgetApp.builders.AssetDtoBuilder;
import com.salem.budgetApp.builders.AssetEntityBuilder;
import com.salem.budgetApp.enums.AssetCategory;
import com.salem.budgetApp.repositories.AssetsRepository;
import com.salem.budgetApp.repositories.entities.AssetEntity;
import com.salem.budgetApp.services.AssetsService;
import com.salem.budgetApp.services.dtos.AssetDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class AssetServiceIntegrationTest {

    @Autowired
    private AssetsRepository repository;
    @Autowired
    private AssetsService service;

    @Test
    void shouldReturnListWithTreeElements(){
        //given
        initDatabase();

        //when
        var allAssetsInDB = service.getAllAssets();

        //then
        assertThat(allAssetsInDB).hasSize(3);
    }

    @Test
    void shouldAddAssetToDB(){

        //given
        AssetDto dto = new AssetDtoBuilder()
                .withAmount(new BigDecimal(4000))
                .withIncomeDate(Instant.now())
                .withCategory(AssetCategory.SALARY)
                .build();
        //when
        service.setAsset(dto);

        //then
        var allAssetsInDB = repository.findAll();
        assertThat(allAssetsInDB).hasSize(1);
        var entity = allAssetsInDB.get(0);
        assertThat(entity.getAmount()).isEqualTo(dto.getAmount());
        assertThat(entity.getIncomeDate()).isEqualTo(dto.getIncomeDate());
        assertThat(entity.getCategory()).isEqualTo(dto.getCategory());
    }

    @Test
    public void shouldReturnListOnlyWithOneCategory(){
        //given
        initDatabase();
        var category = AssetCategory.OTHER;

        //when
        var allAssetsWithOneCategory= service.getAssetByCategory(category);

        //then
        assertThat(allAssetsWithOneCategory).hasSize(1);
        var entity = allAssetsWithOneCategory.get(0);
        assertThat(entity.getCategory()).isEqualTo(category);
    }

    private void initDatabase() {
        AssetEntity entity1 = new AssetEntityBuilder()
                .withAmount(new BigDecimal(10))
                .withInsuranceDate(Instant.now())
                .withCategory(AssetCategory.BONUS)
                .build();
        AssetEntity entity2 = new AssetEntityBuilder()
                .withAmount(new BigDecimal(50))
                .withInsuranceDate(Instant.now())
                .withCategory(AssetCategory.OTHER)
                .build();
        AssetEntity entity3 = new AssetEntityBuilder()
                .withAmount(new BigDecimal(1500))
                .withInsuranceDate(Instant.now())
                .withCategory(AssetCategory.RENT)
                .build();

        repository.saveAll(Arrays.asList(entity1, entity2, entity3));
    }

}
