package com.hotel.system.rooms;

import java.util.List;


public class RoomType {

    private String name;
    private List<String> amenities;
    private int maxOccupancy;

    public RoomType(String name, List<String> amenities, int maxOccupancy) {
        this.name = name;
        this.amenities = amenities;
        this.maxOccupancy = maxOccupancy;
    }

    public String getName() {
        return name;
    }

    public List<String> getAmenities() {
        return amenities;
    }

    public int getMaxOccupancy() {
        return maxOccupancy;
    }

    @Override
    public String toString() {
        return "RoomType{name='" + name + "', amenities=" + amenities + ", maxOccupancy=" + maxOccupancy + "}";
    }


}
