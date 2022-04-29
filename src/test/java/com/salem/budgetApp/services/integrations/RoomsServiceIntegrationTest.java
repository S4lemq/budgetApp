package com.salem.budgetApp.services.integrations;

import com.salem.budgetApp.enums.RoomsType;
import com.salem.budgetApp.services.dtos.RoomsDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RoomsServiceIntegrationTest extends InitIntegrationTestData{

    @Test
    void should_save_one_room_into_data_base() {
        //given
        initDatabaseByPrimeUser();
        var savedType = RoomsType.ROOM_L;
        RoomsDto dto = RoomsDto.builder()
                .type(savedType)
                .cost(BigDecimal.TEN)
                .build();

        //when
        var savedId = roomsService.saveRoom(dto);

        //then
        assertThat(savedId).isNotNull();
        var entityOptional = roomsRepository.findById(savedId);
        var entity = entityOptional.get();
        assertThat(entity.getType()).isEqualTo(savedType);
    }
}
