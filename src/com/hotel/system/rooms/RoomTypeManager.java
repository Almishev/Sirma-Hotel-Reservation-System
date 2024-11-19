package com.hotel.system.rooms;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoomTypeManager {

    private List<RoomType> roomTypes;

    public RoomTypeManager() {
        this.roomTypes = new ArrayList<>();
        loadRoomTypesFromFile();
    }


    private void loadRoomTypesFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("resources/room_types.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0];
                List<String> amenities = Arrays.asList(Arrays.copyOfRange(parts, 1, parts.length - 1));
                int maxOccupancy = Integer.parseInt(parts[parts.length - 1]);
                roomTypes.add(new RoomType(name, amenities, maxOccupancy));
            }
        } catch (IOException e) {
            System.out.println("Error reading room types file: " + e.getMessage());
        }
    }

    public void displayRoomTypes() {
        for (RoomType roomType : roomTypes) {
            System.out.println(roomType);
        }
    }

    public List<RoomType> getRoomTypes() {
        return roomTypes;
    }

    public RoomType findRoomTypeByName(String roomTypeName) {
        for (RoomType roomType : roomTypes) {
            if (roomType.getName().equalsIgnoreCase(roomTypeName)) {
                return roomType;
            }
        }
        return null;
    }


}
