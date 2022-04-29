package com.salem.budgetApp.services.integrations;

import com.salem.budgetApp.builders.PropertyDtoBuilder;
import com.salem.budgetApp.services.dtos.PropertyDto;
import org.junit.jupiter.api.Test;

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
}
