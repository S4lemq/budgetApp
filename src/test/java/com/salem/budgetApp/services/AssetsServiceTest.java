package com.salem.budgetApp.services;

import com.salem.budgetApp.builders.AssetEntityBuilder;
import com.salem.budgetApp.mappers.AssetsMapper;
import com.salem.budgetApp.repositories.AssetsRepository;
import com.salem.budgetApp.repositories.entities.AssetEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;

@ExtendWith(MockitoExtension.class)
public class AssetsServiceTest {

    @Mock
    private AssetsRepository assetsRepository;
    private AssetsMapper assetsMapper = new AssetsMapper();

    private AssetsService service;

    @BeforeEach
    public void init(){
        service = new AssetsService(assetsRepository, assetsMapper);
    }

    @Test
    void shouldReturnListWithOneElementIfThereIsOneElementInDatabase(){
        //given
        var asset = 1;
        AssetEntity assetEntity = new AssetEntityBuilder()
                .withAmount(new BigDecimal(asset))
                .build();
        List<AssetEntity> assetList = Collections.singletonList(assetEntity);
        Mockito.when(assetsRepository.findAll()).thenReturn(assetList);

        //when
        var result = service.getAllAssets();

        //then
        Assertions.assertThat(result.getAssets())
                .hasSize(1)
                .containsExactly(asset);
    }

    @Test
    void shouldReturnListWithTwoElementsIfThereWasTwoElementsInDatabase(){
        //given
        var asset1 = 1;
        var asset2 = 2;
        AssetEntity entityOne = new AssetEntityBuilder()
                .withAmount(new BigDecimal(asset1))
                .build();
        AssetEntity entityTwo = new AssetEntityBuilder()
                .withAmount(new BigDecimal(asset2))
                .build();
        List<AssetEntity> assetsEntity = asList(entityOne,entityTwo);
        Mockito.when(assetsRepository.findAll()).thenReturn(assetsEntity);

        //when
        var result = service.getAllAssets();

        //then
        Assertions.assertThat(result.getAssets())
                .hasSize(2)
                .contains(asset1,asset2);
    }

    @Test
    void shouldVerifyIfTheRepositorySaveWasCalledOneTime(){
        //given
        int asset = 1;
        AssetEntity entity = new AssetEntityBuilder()
                .withAmount(new BigDecimal(asset))
                .build();

        //when
        service.setAsset(asset);

        //then
        Mockito.verify(assetsRepository, Mockito.times(1)).save(entity);
    }
}
