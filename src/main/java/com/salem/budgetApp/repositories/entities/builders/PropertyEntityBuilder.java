package com.salem.budgetApp.repositories.entities.builders;

import com.salem.budgetApp.repositories.entities.PropertyEntity;
import com.salem.budgetApp.repositories.entities.UserEntity;

import java.util.UUID;

public class PropertyEntityBuilder {

    private UUID id;
//    private Integer rooms;
    private Boolean single;
    private String city;
    private String postCode;
    private String street;
    private String house;
    private UserEntity user;

    public PropertyEntityBuilder withId(UUID id){
        this.id = id;
        return this;
    }

//    public PropertyEntityBuilder withRooms(Integer rooms){
//        this.rooms = rooms;
//        return this;
//    }

    public PropertyEntityBuilder withSingle(Boolean single){
        this.single = single;
        return this;
    }

    public PropertyEntityBuilder withCity(String city){
        this.city = city;
        return this;
    }

    public PropertyEntityBuilder withPostCode(String postCode){
        this.postCode = postCode;
        return this;
    }

    public PropertyEntityBuilder withStreet(String street){
        this.street = street;
        return this;
    }

    public PropertyEntityBuilder withHouse(String house){
        this.house = house;
        return this;
    }

    public PropertyEntityBuilder withUser(UserEntity user){
        this.user = user;
        return this;
    }

    public PropertyEntity build(){
        var entity = new PropertyEntity();
        entity.setId(this.id);
//        entity.setRooms(this.rooms);
        entity.setSingle(this.single);
        entity.setCity(this.city);
        entity.setPostCode(this.postCode);
        entity.setStreet(this.street);
        entity.setHouse(this.house);
        entity.setUser(this.user);
        return entity;
    }

}
