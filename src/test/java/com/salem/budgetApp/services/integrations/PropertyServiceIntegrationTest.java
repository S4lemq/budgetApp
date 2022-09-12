package com.salem.budgetApp.services.integrations;

import com.salem.budgetApp.enums.RoomsType;
import com.salem.budgetApp.repositories.entities.PropertyEntity;
import com.salem.budgetApp.repositories.entities.UserEntity;
import com.salem.budgetApp.services.dtos.PropertyDto;
import com.salem.budgetApp.services.dtos.RoomsDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


public class PropertyServiceIntegrationTest extends InitIntegrationTestData{

    @Test
    void should_save_basic_property_in_data_base() {
        //given
        var postCode = "70-649";
        var city = "Poznan";
        var street = "Hetmanska";
        var house = "12A";
        var single = false;

        initDatabaseByPrimeUser();
        PropertyDto property = PropertyDto.builder()
                .postCode(postCode)
                .city(city)
                .street(street)
                .house(house)
                .single(single)
                .build();

        //when
        var result = propertyService.addProperty(property);

        //then
        assertThat(result).isNotNull();
        var entityList = propertyRepository.findAll();
        assertThat(entityList).hasSize(1);
        var entity = entityList.get(0);
        assertAll(
                () -> assertThat(entity.getPostCode()).isEqualTo(postCode),
                () -> assertThat(entity.getCity()).isEqualTo(city),
                () -> assertThat(entity.getStreet()).isEqualTo(street),
                () -> assertThat(entity.getHouse()).isEqualTo(house)
        );
    }
    @Test
    void should_find_one_property_in_data_base() {
        //given
        var user = initDatabaseByPrimeUser();
        initDatabaseByProperty(user);

        //when
        var dtoList = propertyService.findAllProperties(false);

        //then
        assertThat(dtoList).hasSize(1);
    }

    @Test
    void should_update_property_in_database() {
        //given
        var user = initDatabaseByPrimeUser();
        initDatabaseByProperty(user);
        var roomType = RoomsType.ROOM_XL;
        var roomId = initDatabaseByRoom(roomType, BigDecimal.TEN, user);

        PropertyDto property = propertyService.findAllProperties(false).stream().findFirst().get();
        List<PropertyEntity> all = propertyRepository.findAll();
        assertThat(property.getRooms()).isNull();

        var single = property.getSingle();

        var newSingle = !single;
        var newRooms = roomsService.getAll().stream()
                .filter(it -> it.getId().equals(roomId))
                .toList();
        property.setSingle(newSingle);
        property.setRooms(newRooms);

        //when
        propertyService.updateProperty(property);

        //then
        var entity = propertyRepository.findAll().stream().findFirst().get();
        assertThat(entity.getSingle()).isEqualTo(newSingle);
        assertThat(entity.getRooms()).hasSize(1);
        assertThat(entity.getRooms().get(0).getType()).isEqualTo(roomType);

    }

    @Test
    void should_update_rooms_in_property_in_database(){
        //given
        var user = initDatabaseByPrimeUser();
        var roomXlId = initDatabaseByRoom(RoomsType.ROOM_XL, BigDecimal.TEN, user);
        var roomLId = initDatabaseByRoom(RoomsType.ROOM_L, new BigDecimal("8"), user);
        var roomMId = initDatabaseByRoom(RoomsType.ROOM_L, new BigDecimal("4"), user);

        initDatabaseByProperty(user, roomXlId, roomMId);

        var property = propertyService.findAllProperties(false).stream().findFirst().get();

        var newRoomsLAndM = roomsService.getAll().stream()
                .filter(it -> it.getId().equals(roomLId)
                        || it.getId().equals(roomMId))
                .toList();
        property.setRooms(newRoomsLAndM);

        //when
        propertyService.updateProperty(property);

        //then
        var entity = propertyRepository.findAll().stream().findFirst().get();
        assertThat(entity.getRooms()).hasSize(2);
        var containsRoomsLAndM = property.getRooms().stream()
                .map(it -> it.getId())
                .toList()
                .containsAll(asList(roomLId, roomMId));
        assertThat(containsRoomsLAndM).isTrue();
    }

    @Test
    void shouldAddOneRoomToTwoProperties() {
        // given
        var user = initDatabaseByPrimeUser();
        initDatabaseByProperty(user);
        initDatabaseByProperty(user);
        var roomId = initDatabaseByRoom(RoomsType.ROOM_XL, BigDecimal.TEN, user);
        var roomToSet = RoomsDto.builder()
                .id(roomId)
                .build();

        var allProperties = propertyService.findAllProperties(false);
        assertThat(allProperties).hasSize(2);

        var firstProperty = allProperties.get(0);
        var secondProperty = allProperties.get(1);

        firstProperty.setRooms(asList(roomToSet));
        secondProperty.setRooms(asList(roomToSet));

        // when
        propertyService.updateProperty(firstProperty);
        propertyService.updateProperty(secondProperty);

        // then
        var propertiesAfterUpdate = propertyService.findAllProperties(false);
        assertThat(propertiesAfterUpdate).hasSize(2);
        assertThat(propertiesAfterUpdate.get(0).getRooms())
                .isEqualTo(propertiesAfterUpdate.get(1).getRooms());

    }
}
