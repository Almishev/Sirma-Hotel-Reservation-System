package com.hotel.system.menus;

import com.hotel.system.rooms.Room;
import com.hotel.system.rooms.RoomManager;
import com.hotel.system.users.User;
import com.hotel.system.users.UserManager;

import java.util.Scanner;

public class AdminMenu {


    private UserManager userManager;
    private Scanner scanner;

    public AdminMenu(UserManager userManager) {
        this.userManager = userManager;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu(User admin) {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. View All Users");
            System.out.println("2. View Booking Summary");
            System.out.println("3. Add Room");
            System.out.println("4. Remove Room");
            System.out.println("5. Log Out");
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> viewAllUsers();
                case 2 -> viewBookingSummary();
                case 3 -> addRoom();
                case 4 -> removeRoom();
                case 5 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void viewAllUsers() {
        System.out.println("\nAll Registered Users:");
        for (User user : userManager.getAllUsers()) {
            System.out.println(user);
        }
    }

    private void viewBookingSummary() {
        System.out.println("\nBooking Summary:");
        // TODO: Implement logic for viewing booking summary
        System.out.println("Feature under construction.");
    }

    private void addRoom() {
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

            boolean available = true;

            Room newRoom = new Room(roomNumber, roomType, pricePerNight, cancellationFee, available);
            RoomManager roomManager = new RoomManager();
            roomManager.getRooms().add(newRoom);
            roomManager.saveRoomsToFile();
            System.out.println("Room added successfully!");

        } catch (Exception e) {
            System.out.println("Error: Invalid input. Please try again.");
        }
    }


    private void removeRoom() {
        System.out.println("\nRemove Room:");
        try {
            System.out.print("Enter room number to remove: ");
            int roomNumber = Integer.parseInt(scanner.nextLine());

            RoomManager roomManager = new RoomManager();
            Room roomToRemove = roomManager.findRoomByNumber(roomNumber);

            if (roomToRemove != null) {
                roomManager.getRooms().remove(roomToRemove);
                System.out.println("Room " + roomNumber + " removed successfully!");

                // TODO: Save the updated list to the `rooms.txt` file.
            } else {
                System.out.println("Room not found.");
            }
        } catch (Exception e) {
            System.out.println("Error: Invalid input. Please try again.");
        }
    }



}
