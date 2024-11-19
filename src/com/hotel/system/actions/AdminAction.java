package com.hotel.system.actions;

import com.hotel.system.rooms.Room;
import com.hotel.system.rooms.RoomManager;
import com.hotel.system.users.User;
import com.hotel.system.users.UserManager;

import java.util.Scanner;

public class AdminAction extends Action {
    private UserManager userManager;
    private Scanner scanner;

    public AdminAction(RoomManager roomManager, UserManager userManager) {
        super(roomManager);
        this.userManager = userManager;
        this.scanner = new Scanner(System.in);
    }

    public void viewAllUsers() {
        System.out.println("\nAll Registered Users:");
        for (User user : userManager.getAllUsers()) {
            System.out.println(user);
        }
    }

    protected void addRoom() {
        System.out.println("\nAdd Room:");
        try {
            System.out.print("Enter room number: ");
            int roomNumber = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter room type (e.g., Deluxe, Suite): ");
            String roomType = scanner.nextLine();

            System.out.print("Enter price per night: ");
            double pricePerNight = Double.parseDouble(scanner.nextLine());

            System.out.print("Enter cancellation fee: ");
            double cancellationFee = Double.parseDouble(scanner.nextLine());

            Room newRoom = new Room(roomNumber, roomType, pricePerNight, cancellationFee, true);
            roomManager.getRooms().add(newRoom);
            roomManager.saveRoomsToFile();
            System.out.println("Room added successfully!");
        } catch (Exception e) {
            System.out.println("Error: Invalid input. Please try again.");
        }
    }

    protected void removeRoom() {
        System.out.println("\nRemove Room:");
        try {
            System.out.print("Enter room number to remove: ");
            int roomNumber = Integer.parseInt(scanner.nextLine());

            Room roomToRemove = roomManager.findRoomByNumber(roomNumber);
            if (roomToRemove != null) {
                roomManager.getRooms().remove(roomToRemove);
                roomManager.saveRoomsToFile();
                System.out.println("Room " + roomNumber + " removed successfully!");
            } else {
                System.out.println("Room not found.");
            }
        } catch (Exception e) {
            System.out.println("Error: Invalid input. Please try again.");
        }
    }
}
