package com.salem.budgetApp.services.integrations;

import com.salem.budgetApp.builders.AssetDtoBuilder;
import com.salem.budgetApp.enums.AssetCategory;
import com.salem.budgetApp.enums.FilterExceptionErrorMessages;
import com.salem.budgetApp.enums.FilterParametersCalendarEnum;
import com.salem.budgetApp.exceptions.MissingAssetsFilterException;
import com.salem.budgetApp.repositories.entities.UserEntity;
import com.salem.budgetApp.services.dtos.AssetDto;
import org.assertj.core.util.Streams;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AssetServiceIntegrationTest extends InitIntegrationTestData{

    @Test
    void should_return_list_with_three_elements(){
        //given
        initDatabaseByDefaultMockUserAndHisAssets();
        initDatabaseBySecondMockUserAndHisAssets();

        //when
        var allAssetsInDB = assetsService.getAllAssets();

        //then
        assertThat(allAssetsInDB).hasSize(3);
    }

    @Test
    void should_add_asset_to_DB(){
        //given
        initDatabaseByPrimeUser();
        AssetDto dto = new AssetDtoBuilder()
                .withAmount(new BigDecimal(4000))
                .withIncomeDate(Instant.now())
                .withCategory(AssetCategory.SALARY)
                .build();
        //when
        assetsService.setAsset(dto);

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
        initDatabaseByDefaultMockUserAndHisAssets();
        var category = AssetCategory.OTHER;

        //when
        var allAssetsWithOneCategory= assetsService.getAssetByCategory(category);

        //then
        assertThat(allAssetsWithOneCategory).hasSize(1);
        var entity = allAssetsWithOneCategory.get(0);
        assertThat(entity.getCategory()).isEqualTo(category);
    }

    @Test
    void should_delete_all_assets_of_chosen_user(){
        //given
        initDatabaseByDefaultMockUserAndHisAssets();
        initDatabaseBySecondMockUserAndHisAssets();
        int numberOfAllAssets = 6;
        int numberOfLeaveAssets = 3;

        var allUsers = userRepository.findAll();
        var userToDeleteAssets = Streams.stream(allUsers).findFirst();
        UserEntity userEntity = userToDeleteAssets.get();
        var userToLeaveAssets = Streams.stream(allUsers)
                .filter(entity -> !entity.equals(userEntity))
                .findFirst().get();

        var allAssetsInDatabase = assetsRepository.findAll();
        assertThat(allAssetsInDatabase).hasSize(numberOfAllAssets);

        //when
        assetsService.deleteAllAssetsByUser(userEntity);

        //then
        var assetsAfterDelete = assetsRepository.findAll();
        assertThat(assetsAfterDelete).hasSize(numberOfLeaveAssets);

        var assetsUserId = assetsAfterDelete.stream()
                .map(assetEntity -> assetEntity.getUser())
                .map(ue -> ue.getId())
                .distinct()
                .collect(Collectors.toList());

        assertThat(assetsUserId).hasSize(1).containsExactly(userToLeaveAssets.getId());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource
    void should_throw_exception_when_one_of_the_filters_key(String testName, ParameterTestData testData) {
        //given
        initDatabaseByPrimeUser();

        //when
        var result = assertThrows(MissingAssetsFilterException.class,
                () -> assetsService.getAssetsByFilter(testData.filter));

        //then
        assertThat(result.getMessage())
                .isEqualTo(FilterExceptionErrorMessages.MISSING_ASSETS_FILTER_KEY.getMessage(testData.missingKey.getKey()));
    }

    private static Stream<Arguments> should_throw_exception_when_one_of_the_filters_key(){
        return Stream.of(
                Arguments.of("test for missing " + FilterParametersCalendarEnum.TO_DATE.getKey(),
                        new ParameterTestData(
                                new HashMap<>(){{
                                    put(FilterParametersCalendarEnum.TO_DATE.getKey(), "2020-02-20");
                                }},
                                FilterParametersCalendarEnum.FROM_DATE)
                ),

                Arguments.of("test for missing " + FilterParametersCalendarEnum.FROM_DATE.getKey(),
                        new ParameterTestData(
                                new HashMap<>(){{
                                    put(FilterParametersCalendarEnum.FROM_DATE.getKey(), "2020-02-20");
                                }},
                                FilterParametersCalendarEnum.TO_DATE)
                ),
                Arguments.of("test for missing " + FilterParametersCalendarEnum.MONTH.getKey(),
                        new ParameterTestData(
                                new HashMap<>(){{
                                    put(FilterParametersCalendarEnum.MONTH.getKey(), "january");
                                }},
                                FilterParametersCalendarEnum.YEAR)
                ),
                Arguments.of("test for missing " + FilterParametersCalendarEnum.YEAR.getKey(),
                        new ParameterTestData(
                                new HashMap<>(){{
                                    put(FilterParametersCalendarEnum.YEAR.getKey(), "2020-02-20");
                                }},
                                FilterParametersCalendarEnum.MONTH)
                )
        );
    }

    private static class ParameterTestData{

        public Map<String, String> filter;
        public FilterParametersCalendarEnum missingKey;

        public ParameterTestData(Map<String, String> filter, FilterParametersCalendarEnum missingKey) {
            this.filter = filter;
            this.missingKey = missingKey;
        }
    }

}
