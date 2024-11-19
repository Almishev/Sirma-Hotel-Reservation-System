package com.hotel.system.menus;

import com.hotel.system.rooms.Room;
import com.hotel.system.rooms.RoomManager;
import com.hotel.system.users.User;
import com.hotel.system.users.UserManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class AdminMenu {


    private UserManager userManager;

    private RoomManager roomManager;
    private Scanner scanner = new Scanner(System.in);

    public AdminMenu(UserManager userManager, RoomManager roomManager) {
        this.userManager = userManager;
        this.roomManager= roomManager;
    }

    public boolean showMenu(User admin) {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. View All Users");
            System.out.println("2. View Booking Summary");
            System.out.println("3. Add Room");
            System.out.println("4. Remove Room");
            System.out.println("5. Change Room status");
            System.out.println("6. Log Out");
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> viewAllUsers();
                case 2 -> viewBookingSummary();
                case 3 -> addRoom();
                case 4 -> removeRoom();
                case 5 -> adminUpdateRoomStatus();
                case 6 -> {
                    System.out.println("Logging out...");
                    return false;
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
        try (BufferedReader br = new BufferedReader(new FileReader("resources/booking.txt"))) {
            String line;
            if (!br.ready()) {
                System.out.println("No bookings available.");
                return;
            }
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    System.out.printf("User: %s | Room: %s | Type: %s | From: %s | To: %s%n",
                            parts[0], parts[1], parts[2], parts[3], parts[4]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading booking summary: " + e.getMessage());
        }
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
                roomManager.saveRoomsToFile();
                System.out.println("Room " + roomNumber + " removed successfully!");
            } else {
                System.out.println("Room not found.");
            }
        } catch (Exception e) {
            System.out.println("Error: Invalid input. Please try again.");
        }

    }



    public void adminUpdateRoomStatus() {

        System.out.print("Enter the room number to update: ");
        int roomNumber = scanner.nextInt();
        scanner.nextLine(); // clear the buffer

        System.out.print("Enter the new status (available/booked): ");
        String statusInput = scanner.nextLine().toLowerCase();

        boolean isAvailable = statusInput.equals("available");
        roomManager.updateRoomStatus(roomNumber, isAvailable);
    }

}
