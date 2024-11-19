package com.hotel.system.rooms;


import java.io.*;
import java.util.Arrays;
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

                if (parts.length != 5) {
                    System.out.println("Грешка: Невалиден формат на реда в rooms.txt (ред: " + line + ")");
                    continue;
                }

                int roomNumber = Integer.parseInt(parts[0]);
                String roomTypeName = parts[1].trim();
                double pricePerNight = Double.parseDouble(parts[2]);
                double cancellationFee = Double.parseDouble(parts[3]);
                boolean available = parts[4].equalsIgnoreCase("available");


                RoomType type = createRoomType(roomTypeName);

                if (type == null) {
                    System.out.println("Грешка: Невалиден тип стая: " + roomTypeName);
                    continue;
                }

                Room newRoom = new Room(roomNumber, type, pricePerNight, cancellationFee, available);
                rooms.add(newRoom);
            }
        } catch (IOException e) {
            System.out.println("Грешка при четене на файла със стаите: " + e.getMessage());
        }
    }

    private RoomType createRoomType(String name) {
        // Логика за създаване на RoomType обект въз основа на името
        switch (name) {
            case "Deluxe":
                return new RoomType(name, List.of("WiFi", "TV", "Minibar"), 2);
            case "Single":
                return new RoomType(name, List.of("WiFi"), 1);
            case "Suite":
                return new RoomType(name, List.of("WiFi", "TV", "Minibar", "Balcony"), 4);
            default:
                System.out.println("Неизвестен тип стая: " + name);
                return null;
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

    public List<Room> getAvailableRooms(String roomTypeName, String startDate, String endDate) {
        List<Room> availableRooms = new ArrayList<>();

        for (Room room : rooms) {
            if (room.getType().getName().equalsIgnoreCase(roomTypeName) &&
                    room.isAvailable() &&
                    room.isAvailableForDates(startDate, endDate)) {
                availableRooms.add(room);
            }
        }

        return availableRooms;
    }


    public boolean checkRoomAvailability(String roomType, String startDate, String endDate) {
        List<Room> availableRooms = getAvailableRooms(roomType, startDate, endDate);
        return !availableRooms.isEmpty();
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
