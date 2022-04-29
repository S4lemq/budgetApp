package com.salem.budgetApp.services.integrations;

import com.salem.budgetApp.builders.PropertyDtoBuilder;
import com.salem.budgetApp.builders.PropertyEntityBuilder;
import com.salem.budgetApp.repositories.entities.PropertyEntity;
import com.salem.budgetApp.services.dtos.PropertyDto;
import org.assertj.core.util.Streams;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

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
        var rooms = 3;

        initDatabaseByPrimeUser();
        PropertyDto property = new PropertyDtoBuilder()
                .withPostCode(postCode)
                .withCity(city)
                .withStreet(street)
                .withHouse(house)
                .withSingle(single)
                .withRooms(rooms)
                .build();

        //when
        var result = propertyService.addProperty(property);

        //then
        assertThat(result).isNotNull();
        var entityList = propertyRepository.findAll();
        assertThat(entityList).hasSize(1);
        var entity = entityList.get(0);
        assertAll(
                () -> assertThat(entity.getRooms()).isEqualTo(rooms),
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
        var dtoList = propertyService.findAllProperties();

        //then
        assertThat(dtoList).hasSize(1);
    }

    @Test
    void should_delete_property_from_data_base() {
        //given
        initDatabaseByDefaultMockUserAndHisProperty();
        int numberOfLeaveProperty = 2;

        var dataBaseBeforeDelete = propertyRepository.findAll();
        var entityIdToDelete = propertyRepository.findAll().get(0).getId();
        var entityToDelete = propertyRepository.findById(entityIdToDelete).get();

        //when
        propertyService.deleteProperty(propertyMapper.fromEntityToDto(entityToDelete));

        //then
        assertThat(propertyRepository.findAll()).hasSize(numberOfLeaveProperty);
        var dataBaseWithoutDeletedProperty = Streams.stream(dataBaseBeforeDelete)
                .filter(entity -> !entity.equals(entityToDelete))
                .collect(Collectors.toList());
        assertThat(propertyRepository.findAll()).isEqualTo(dataBaseWithoutDeletedProperty);
    }

    @Test
    void should_update_property_in_database() {
        //given
        var newPostCode = "00-000";
        var newCity = "Bialystok";
        var newStreet = "Piekna";
        var newHouse = "00";
        var newSingle = true;
        var newRooms = 10;

        initDatabaseByDefaultMockUserAndHisProperty();
        var entityId = propertyRepository.findAll().get(0).getId();
        PropertyEntity changedProperty = new PropertyEntityBuilder()
                .withId(entityId)
                .withPostCode(newPostCode)
                .withCity(newCity)
                .withStreet(newStreet)
                .withHouse(newHouse)
                .withSingle(newSingle)
                .withRooms(newRooms)
                .withUser(propertyRepository.findById(entityId).get().getUser())
                .build();

        //when
        propertyService.updateProperty(propertyMapper.fromEntityToDto(changedProperty));

        //then
        assertThat(propertyRepository.findById(entityId)
                .get().getPostCode()).isEqualTo(newPostCode);
        assertThat(propertyRepository.findById(entityId)
                .get().getCity()).isEqualTo(newCity);
        assertThat(propertyRepository.findById(entityId)
                .get().getStreet()).isEqualTo(newStreet);
        assertThat(propertyRepository.findById(entityId)
                .get().getHouse()).isEqualTo(newHouse);
        assertThat(propertyRepository.findById(entityId)
                .get().getSingle()).isEqualTo(newSingle);
        assertThat(propertyRepository.findById(entityId)
                .get().getRooms()).isEqualTo(newRooms);
    }
}
