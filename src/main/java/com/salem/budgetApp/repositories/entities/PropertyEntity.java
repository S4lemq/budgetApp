package com.salem.budgetApp.repositories.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "property")
public class PropertyEntity extends BaseBudgetEntity{

    private Integer rooms;
    private Boolean single;
    private String city;
    private String postCode;
    private String street;
    private String house;

    public Integer getRooms() {
        return rooms;
    }

    public void setRooms(Integer rooms) {
        this.rooms = rooms;
    }

    public Boolean getSingle() {
        return single;
    }

    public void setSingle(Boolean single) {
        this.single = single;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertyEntity that = (PropertyEntity) o;
        return Objects.equals(city, that.city) && Objects.equals(postCode, that.postCode) && Objects.equals(street, that.street) && Objects.equals(house, that.house);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, postCode, street, house);
    }

    @Override
    public String toString() {
        return "PropertyEntity{" +
                "rooms=" + rooms +
                ", single=" + single +
                ", city='" + city + '\'' +
                ", postCode='" + postCode + '\'' +
                ", street='" + street + '\'' +
                ", house='" + house + '\'' +
                '}';
    }
}
