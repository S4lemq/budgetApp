package com.salem.budgetApp.services;

import com.salem.budgetApp.builders.AssetDtoBuilder;
import com.salem.budgetApp.builders.AssetEntityBuilder;
import com.salem.budgetApp.enums.ValidatorsAssetEnum;
import com.salem.budgetApp.exceptions.AssetIncompleteException;
import com.salem.budgetApp.mappers.AssetsMapper;
import com.salem.budgetApp.repositories.AssetsRepository;
import com.salem.budgetApp.repositories.entities.AssetEntity;
import com.salem.budgetApp.services.dtos.AssetDto;
import com.salem.budgetApp.validators.AssetValidator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class AssetsServiceTest {

    @Mock
    private AssetsRepository assetsRepository;
    @Mock
    private UserLogInfoService userLogInfoService;

    private AssetsMapper assetsMapper = new AssetsMapper();
    private AssetValidator assetValidator = new AssetValidator();

    private AssetsService service;

    @BeforeEach
    public void init(){
        service = new AssetsService(assetsRepository, assetsMapper, assetValidator, userLogInfoService);
    }

    @Test
    void should_return_list_with_one_element_if_there_is_one_element_in_database(){
        //given
        var asset = BigDecimal.ONE;
        AssetEntity assetEntity = new AssetEntityBuilder()
                .withAmount(asset)
                .build();
        List<AssetEntity> assetList = Collections.singletonList(assetEntity);
        Mockito.when(assetsRepository.getAssetEntitiesByUser(any())).thenReturn(assetList);

        //when
        var result = service.getAllAssets();

        //then
        Assertions.assertThat(result)
                .hasSize(1)
                .contains(new AssetDtoBuilder().withAmount(asset).build());
    }

    @Test
    void should_return_list_with_two_elements_if_there_was_two_elements_in_database(){
        //given
        var asset1 = BigDecimal.ONE;
        var asset2 = new BigDecimal("2");
        AssetEntity entityOne = new AssetEntityBuilder()
                .withAmount(asset1)
                .build();
        AssetEntity entityTwo = new AssetEntityBuilder()
                .withAmount(asset2)
                .build();
        List<AssetEntity> assetsEntity = asList(entityOne,entityTwo);
        Mockito.when(assetsRepository.getAssetEntitiesByUser(any())).thenReturn(assetsEntity);

        //when
        var result = service.getAllAssets();

        //then
        Assertions.assertThat(result)
                .hasSize(2)
                .containsExactly(
                        new AssetDtoBuilder().withAmount(asset1).build(),
                        new AssetDtoBuilder().withAmount(asset2).build()
                );
    }

    @Test
    void should_verify_if_the_repository_save_was_called_one_time(){
        //given
        BigDecimal asset = BigDecimal.ONE;
        Instant incomeDate = Instant.now();
        AssetDto dto = new AssetDtoBuilder()
                .withAmount(asset)
                .withIncomeDate(incomeDate)
                .build();
        AssetEntity entity = new AssetEntityBuilder()
                .withAmount(asset)
                .build();

        //when
        service.setAsset(dto);

        //then
        Mockito.verify(assetsRepository, Mockito.times(1)).save(entity);
    }

    @Test
    void should_throw_exception_when_amount_in_assetDto_is_null(){
        //given
        AssetDto dto = new AssetDtoBuilder()
                .withIncomeDate(Instant.now())
                .build();

        //when
        var result = assertThrows(AssetIncompleteException.class, () -> service.setAsset(dto));

        //then
        assertEquals(ValidatorsAssetEnum.NO_AMOUNT.getMessage(), result.getMessage());
    }

    @Test
    void should_verify_if_the_repository_update_was_called(){
        //given
        BigDecimal asset = BigDecimal.ONE;
        var dto = new AssetDtoBuilder().withAmount(asset).build();
        var entity = new AssetEntityBuilder().withAmount(asset).build();
        Mockito.when(assetsRepository.findById(any())).thenReturn(Optional.of(entity));

        //when
        service.updateAsset(dto);

        //then
        Mockito.verify(assetsRepository, Mockito.times(1)).saveAndFlush(entity);
    }

    @Test
    void should_throw_exception_when_IncomeDate_in_assetDto_is_null(){
        //given
        BigDecimal asset = BigDecimal.ONE;
        AssetDto dto = new AssetDtoBuilder()
                .withAmount(asset)
                .build();

        //when
        var result = assertThrows(AssetIncompleteException.class, () -> service.setAsset(dto));

        //then
        assertEquals(ValidatorsAssetEnum.NO_INCOME_DATE.getMessage(), result.getMessage());
    }

    @Test
    void should_throw_exception_when_IncomeDate_and_amount_in_assetDto_is_null(){
        //given
        AssetDto dto = new AssetDto();
        String messageSeparator = ", ";
        String expectedMessage = ValidatorsAssetEnum.NO_AMOUNT.getMessage()
                + messageSeparator
                + ValidatorsAssetEnum.NO_INCOME_DATE.getMessage();

        //when
        var result = assertThrows(AssetIncompleteException.class, () -> service.setAsset(dto));

        //then
        assertEquals(expectedMessage, result.getMessage());
    }


}
