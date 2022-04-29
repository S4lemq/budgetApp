package com.salem.budgetApp.enums;

public enum RoomsType {

    ROOM_XS("size xs"),
    ROOM_S("size s"),
    ROOM_M("size M"),
    ROOM_L("size L"),
    ROOM_XL("size XL"),
    ROOM_XLL("size XLL");

    public final String sizeName;

    RoomsType(String sizeName) {
        this.sizeName = sizeName;
    }

    public String getSizeName(){
        return sizeName;
    }
}
