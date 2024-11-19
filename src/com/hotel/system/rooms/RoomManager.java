package com.hotel.system.rooms;


import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class RoomManager {
    private List<Room> rooms;

    public RoomManager() {
        this.rooms = new ArrayList<>();
        loadRoomsFromFile();
    }

    private void loadRoomsFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("resources/rooms.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int roomNumber = Integer.parseInt(parts[0]);
                String type = parts[1];
                double pricePerNight = Double.parseDouble(parts[2]);
                double cancellationFee = Double.parseDouble(parts[3]);
                boolean available = parts[4].equalsIgnoreCase("available");
                rooms.add(new Room(roomNumber, type, pricePerNight, cancellationFee, available));
            }
        } catch (IOException e) {
            System.out.println("Error reading rooms file: " + e.getMessage());
        }
    }

    public void displayRooms() {
        for (Room room : rooms) {
            System.out.println(room);
        }
    }

    public Room findRoomByNumber(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }

    public void updateRoomStatus(int roomNumber, boolean isAvailable) {
        Room room = findRoomByNumber(roomNumber);
        if (room != null) {
            room.setAvailable(isAvailable);
            saveRoomsToFile();
            System.out.println("Room " + roomNumber + " status updated to " + (isAvailable ? "Available" : "Booked"));
        } else {
            System.out.println("Room " + roomNumber + " not found.");
        }
    }


    public List<Room> getRooms() {
        return rooms;
    }

    public List<Room> getAvailableRooms(String roomType, String startDate, String endDate) {
        List<Room> availableRooms = new ArrayList<>();

        for (Room room : rooms) {
            if (room.getType().equalsIgnoreCase(roomType) &&
                    room.isAvailable() &&
                    room.isAvailableForDates(startDate, endDate)) {
                availableRooms.add(room);
            }
        }

        return availableRooms;
    }


    public boolean checkRoomAvailability(String roomType, String startDate, String endDate) {
        List<Room> availableRooms = getAvailableRooms(roomType, startDate, endDate);
        return !availableRooms.isEmpty(); // Връща true, ако има налични стаи
    }


    public void saveRoomsToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("resources/rooms.txt"))) {
            for (Room room : rooms) {
                bw.write(room.getRoomNumber() + "," + room.getType() + "," +
                        room.getPricePerNight() + "," + room.getCancellationFee() + "," +
                        (room.isAvailable() ? "available" : "booked"));
                bw.newLine();
            }
            System.out.println("Rooms successfully saved to file.");
        } catch (IOException e) {
            System.out.println("Error writing to rooms file: " + e.getMessage());
        }
    }
}
