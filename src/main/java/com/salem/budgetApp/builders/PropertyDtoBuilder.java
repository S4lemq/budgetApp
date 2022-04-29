package com.salem.budgetApp.builders;

import com.salem.budgetApp.services.dtos.PropertyDto;

import java.util.UUID;

public class PropertyDtoBuilder {

    private UUID id;
    private Integer rooms;
    private Boolean single;
    private String city;
    private String postCode;
    private String street;
    private String house;

    public PropertyDtoBuilder withId(UUID id){
        this.id = id;
        return this;
    }

    public PropertyDtoBuilder withRooms(Integer rooms){
        this.rooms = rooms;
        return this;
    }

    public PropertyDtoBuilder withSingle(Boolean single){
        this.single = single;
        return this;
    }

    public PropertyDtoBuilder withCity(String city){
        this.city = city;
        return this;
    }

    public PropertyDtoBuilder withPostCode(String postCode){
        this.postCode = postCode;
        return this;
    }

    public PropertyDtoBuilder withStreet(String street){
        this.street = street;
        return this;
    }

    public PropertyDtoBuilder withHouse(String house){
        this.house = house;
        return this;
    }

    public PropertyDto build(){
        PropertyDto dto = new PropertyDto();
        dto.setId(this.id);
        dto.setRooms(this.rooms);
        dto.setSingle(this.single);
        dto.setCity(this.city);
        dto.setPostCode(this.postCode);
        dto.setStreet(this.street);
        dto.setHouse(this.house);
        return dto;
    }

}
