package com.salem.budgetApp.services.integrations;

import com.salem.budgetApp.builders.AssetDtoBuilder;
import com.salem.budgetApp.builders.AssetEntityBuilder;
import com.salem.budgetApp.enums.AssetCategory;
import com.salem.budgetApp.repositories.AssetsRepository;
import com.salem.budgetApp.repositories.UserRepository;
import com.salem.budgetApp.repositories.entities.AssetEntity;
import com.salem.budgetApp.repositories.entities.UserEntity;
import com.salem.budgetApp.services.AssetsService;
import com.salem.budgetApp.services.dtos.AssetDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@WithMockUser(username = "user123", password = "123user")
public class AssetServiceIntegrationTest {

    @Autowired
    private AssetsRepository assetsRepository;
    @Autowired
    private AssetsService service;
    @Autowired
    private UserRepository userRepository;

    @Test
    void should_return_list_with_three_elements(){
        //given
        init_database_by_default_mock_user_and_his_assets();
        init_database_by_second_mock_user_and_his_assets();

        //when
        var allAssetsInDB = service.getAllAssets();

        //then
        assertThat(allAssetsInDB).hasSize(3);
    }

    @Test
    void should_add_asset_to_DB(){
        //given
        init_default_mock_user_in_database();
        AssetDto dto = new AssetDtoBuilder()
                .withAmount(new BigDecimal(4000))
                .withIncomeDate(Instant.now())
                .withCategory(AssetCategory.SALARY)
                .build();
        //when
        service.setAsset(dto);

        //then
        var allAssetsInDB = assetsRepository.findAll();
        assertThat(allAssetsInDB).hasSize(1);
        var entity = allAssetsInDB.get(0);
        assertThat(entity.getAmount()).isEqualTo(dto.getAmount());
        assertThat(entity.getIncomeDate()).isEqualTo(dto.getIncomeDate());
        assertThat(entity.getCategory()).isEqualTo(dto.getCategory());
    }

    @Test
    public void should_return_list_only_with_one_category(){
        //given
        init_database_by_default_mock_user_and_his_assets();
        var category = AssetCategory.OTHER;

        //when
        var allAssetsWithOneCategory= service.getAssetByCategory(category);

        //then
        assertThat(allAssetsWithOneCategory).hasSize(1);
        var entity = allAssetsWithOneCategory.get(0);
        assertThat(entity.getCategory()).isEqualTo(category);
    }

    private UserEntity init_default_mock_user_in_database(){
        var user = new UserEntity();
        user.setUsername("user123");
        user.setPassword("123user");

        return userRepository.save(user);
    }

    private void init_database_by_default_mock_user_and_his_assets() {
        var userEntity = init_default_mock_user_in_database();
        AssetEntity entity1 = new AssetEntityBuilder()
                .withAmount(new BigDecimal(10))
                .withInsuranceDate(Instant.now())
                .withCategory(AssetCategory.BONUS)
                .withUser(userEntity)
                .build();
        AssetEntity entity2 = new AssetEntityBuilder()
                .withAmount(new BigDecimal(50))
                .withInsuranceDate(Instant.now())
                .withCategory(AssetCategory.OTHER)
                .withUser(userEntity)
                .build();
        AssetEntity entity3 = new AssetEntityBuilder()
                .withAmount(new BigDecimal(1500))
                .withInsuranceDate(Instant.now())
                .withCategory(AssetCategory.RENT)
                .withUser(userEntity)
                .build();

        assetsRepository.saveAll(Arrays.asList(entity1, entity2, entity3));
    }

    private UserEntity init_second_mock_user_in_database(){
        var user = new UserEntity();
        user.setUsername("secondUser123");
        user.setPassword("123SecondUser");

        return userRepository.save(user);
    }

    private void init_database_by_second_mock_user_and_his_assets() {
        var userEntity = init_second_mock_user_in_database();
        AssetEntity entity1 = new AssetEntityBuilder()
                .withAmount(new BigDecimal(10))
                .withInsuranceDate(Instant.now())
                .withCategory(AssetCategory.BONUS)
                .withUser(userEntity)
                .build();
        AssetEntity entity2 = new AssetEntityBuilder()
                .withAmount(new BigDecimal(50))
                .withInsuranceDate(Instant.now())
                .withCategory(AssetCategory.OTHER)
                .withUser(userEntity)
                .build();
        AssetEntity entity3 = new AssetEntityBuilder()
                .withAmount(new BigDecimal(1500))
                .withInsuranceDate(Instant.now())
                .withCategory(AssetCategory.RENT)
                .withUser(userEntity)
                .build();

        assetsRepository.saveAll(Arrays.asList(entity1, entity2, entity3));
    }

}
