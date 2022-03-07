package com.salem.budgetApp.services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class AssetsServicesTest {

    @Test
    void shouldSaveAssetAndReturnListWithOneElementIfThereWasNoSaveAssetsBefore(){
        //given
        var asset = 1;
        var service = new AssetsServices();
        service.setAsset(asset);

        //when
        var result = service.getAssets();

        //then
        Assertions.assertThat(result.getAssets())
                .hasSize(1)
                .containsExactly(asset);
    }

    @Test
    void shouldSaveAssetAndReturnListWithTwoElementsIfThereWasNoSavedAssetsBefore(){
        //given
        var asset1 = 1;
        var asset2 = 2;
        var service = new AssetsServices();
        service.setAsset(asset1);
        service.setAsset(asset2);

        //when
        var result = service.getAssets();

        //then
        Assertions.assertThat(result.getAssets())
                .hasSize(2)
                .contains(asset1,asset2);
    }
}
