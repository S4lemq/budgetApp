package com.salem.budgetApp.services.integrations;

import com.salem.budgetApp.enums.AssetCategory;
import com.salem.budgetApp.enums.FilterExceptionErrorMessages;
import com.salem.budgetApp.enums.FilterParametersEnum;
import com.salem.budgetApp.enums.MonthsEnum;
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

import static java.util.Arrays.asList;
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
        String description = "some description";
        AssetDto dto = AssetDto.builder()
                .amount(new BigDecimal(4000))
                .incomeDate(Instant.now())
                .category(AssetCategory.SALARY)
                .description(description)
                .build();
        //when
        assetsService.setAsset(asList(dto));

        //then
        var allAssetsInDB = assetsRepository.findAll();
        assertThat(allAssetsInDB).hasSize(1);
        var entity = allAssetsInDB.get(0);
        assertThat(entity.getAmount()).isEqualTo(dto.getAmount());
        assertThat(entity.getIncomeDate()).isEqualTo(dto.getIncomeDate());
        assertThat(entity.getCategory()).isEqualTo(dto.getCategory());
        assertThat(entity.getDescription()).isEqualTo(dto.getDescription());
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

    @Test
    void should_get_all_assets_by_filter_by_date_from_to() {
        //given
        var fromDate = "2022-03-01";
        var toDate = "2022-03-30";
        var middleDate = "2022-03-15";
        var outOfRangeDate = "2022-04-17";
        var user = initDatabaseByPrimeUser();
        initDatabaseByAssetsForUser(user, fromDate);
        initDatabaseByAssetsForUser(user, toDate);
        initDatabaseByAssetsForUser(user, middleDate);
        initDatabaseByAssetsForUser(user, outOfRangeDate);

        Map<String, String> filter = new HashMap<>() {{
            put(FilterParametersEnum.FROM_DATE.getKey(), fromDate);
            put(FilterParametersEnum.TO_DATE.getKey(), toDate);
        }};

        //when
        var result = assetsService.getAssetsByFilter(filter);

        //then
        assertThat(result).hasSize(3);
        var dateAsString = result.stream()
                .map(dto -> dto.getIncomeDate().toString().substring(0, fromDate.length()))
                .collect(Collectors.toList());
        assertThat(dateAsString).hasSize(3)
                .contains(fromDate, toDate, middleDate)
                .doesNotContain(outOfRangeDate);
    }

    @Test
    void should_get_all_assets_by_filter_by_date_from_to_and_category() {
        //given
        var lookingCategory = AssetCategory.BONUS;
        var notLookingCategory = AssetCategory.SALARY;
        var fromDate = "2022-03-01";
        var toDate = "2022-03-30";
        var middleDate = "2022-03-15";
        var outOfRangeDate = "2022-04-17";
        var user = initDatabaseByPrimeUser();
        initDatabaseByAssetsForUser(user, fromDate, notLookingCategory);
        initDatabaseByAssetsForUser(user, toDate, notLookingCategory);
        initDatabaseByAssetsForUser(user, middleDate, lookingCategory);
        initDatabaseByAssetsForUser(user, outOfRangeDate, lookingCategory);

        Map<String, String> filter = new HashMap<>() {{
            put(FilterParametersEnum.FROM_DATE.getKey(), fromDate);
            put(FilterParametersEnum.TO_DATE.getKey(), toDate);
            put(FilterParametersEnum.CATEGORY.getKey(), lookingCategory.name());
        }};

        //when
        var result = assetsService.getAssetsByFilter(filter);

        //then
        assertThat(result).hasSize(1);
        var dateAsString = result.stream()
                .map(dto -> dto.getIncomeDate().toString().substring(0, fromDate.length()))
                .collect(Collectors.toList());
        assertThat(dateAsString).hasSize(1)
                .contains(middleDate)
                .doesNotContain(fromDate, toDate, outOfRangeDate);
    }

    @Test
    void should_get_all_assets_by_filter_by_month_year() {
        //given
        var fromDate = "2022-03-01";
        var toDate = "2022-03-30";
        var middleDate = "2022-03-15";
        var outOfRangeDate = "2022-04-17";
        var user = initDatabaseByPrimeUser();
        initDatabaseByAssetsForUser(user, fromDate);
        initDatabaseByAssetsForUser(user, toDate);
        initDatabaseByAssetsForUser(user, middleDate);
        initDatabaseByAssetsForUser(user, outOfRangeDate);

        Map<String, String> filter = new HashMap<>() {{
            put(FilterParametersEnum.MONTH.getKey(), MonthsEnum.MARCH.name());
            put(FilterParametersEnum.YEAR.getKey(), "2022");
        }};

        //when
        var result = assetsService.getAssetsByFilter(filter);

        //then
        assertThat(result).hasSize(3);
        var dateAsString = result.stream()
                .map(dto -> dto.getIncomeDate().toString().substring(0, fromDate.length()))
                .collect(Collectors.toList());
        assertThat(dateAsString).hasSize(3)
                .contains(fromDate, toDate, middleDate)
                .doesNotContain(outOfRangeDate);
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
                Arguments.of("test for missing " + FilterParametersEnum.TO_DATE.getKey(),
                        new ParameterTestData(
                                new HashMap<>(){{
                                    put(FilterParametersEnum.TO_DATE.getKey(), "2020-02-20");
                                }},
                                FilterParametersEnum.FROM_DATE)
                ),

                Arguments.of("test for missing " + FilterParametersEnum.FROM_DATE.getKey(),
                        new ParameterTestData(
                                new HashMap<>(){{
                                    put(FilterParametersEnum.FROM_DATE.getKey(), "2020-02-20");
                                }},
                                FilterParametersEnum.TO_DATE)
                ),
                Arguments.of("test for missing " + FilterParametersEnum.MONTH.getKey(),
                        new ParameterTestData(
                                new HashMap<>(){{
                                    put(FilterParametersEnum.MONTH.getKey(), "january");
                                }},
                                FilterParametersEnum.YEAR)
                ),
                Arguments.of("test for missing " + FilterParametersEnum.YEAR.getKey(),
                        new ParameterTestData(
                                new HashMap<>(){{
                                    put(FilterParametersEnum.YEAR.getKey(), "2020-02-20");
                                }},
                                FilterParametersEnum.MONTH)
                )
        );
    }

    private static class ParameterTestData{

        public Map<String, String> filter;
        public FilterParametersEnum missingKey;

        public ParameterTestData(Map<String, String> filter, FilterParametersEnum missingKey) {
            this.filter = filter;
            this.missingKey = missingKey;
        }
    }

}
