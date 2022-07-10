package com.salem.budgetApp.services.integrations;

import com.salem.budgetApp.enums.RoomsType;
import com.salem.budgetApp.services.dtos.RoomsDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;

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
        var savedId = roomsService.saveOrUpdate(dto);

        //then
        assertThat(savedId).isNotNull();
        var entityOptional = roomsRepository.findById(savedId);
        var entity = entityOptional.get();
        assertThat(entity.getType()).isEqualTo(savedType);
    }

    @Test
    void should_update_room_in_database() {
        //given
        var user = initDatabaseByPrimeUser();
        var type = RoomsType.ROOM_XL;
        var oldCost = BigDecimal.ONE;
        var newCost = BigDecimal.TEN;
        var roomId = initDatabaseByRoom(type, oldCost, user);

        //when
        var dtoToChange = RoomsDto.builder()
                .id(roomId)
                .cost(newCost)
                .type(type)
                .build();
        roomsService.saveOrUpdate(dtoToChange);

        //then
        var entityOptional = roomsRepository.findById(roomId);
        var entity = entityOptional.get();
        assertThat(entity.getCost()).isEqualTo(newCost);
    }

    @Test
    void should_make_room_type_inactive() {
        //given
        var user = initDatabaseByPrimeUser();
        var roomId = initDatabaseByRoom(RoomsType.ROOM_L, new BigDecimal("2"), user);
        //when
        roomsService.inactiveRoom(roomId);
        //then
        var entityOptional = roomsRepository.findById(roomId);
        var entity = entityOptional.get();
        assertThat(entity.getCost()).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void should_get_all_rooms_with_non_cost_if_there_is_no_room_in_database() {
        //given
        initDatabaseByPrimeUser();
        //when
        var allRooms = roomsService.getAll();
        //then
        assertThat(allRooms.size()).isEqualTo(6);
        assertThat(allRooms.get(0).getCost()).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void should_get_all_rooms_with_some_cost_if_there_is_some_room_in_database() {
        //given
        var user = initDatabaseByPrimeUser();
        Arrays.asList(RoomsType.values()).forEach(
                roomsType -> initDatabaseByRoom(roomsType, BigDecimal.TEN, user)
        );
        //when
        var allRooms = roomsService.getAll();
        //then
        assertThat(allRooms.size()).isEqualTo(6);
        assertThat(allRooms.get(0).getCost()).isEqualTo(BigDecimal.TEN);
    }
}