package com.hotel.system.menus;

import com.hotel.system.booking.BookingManager;
import com.hotel.system.rooms.Room;
import com.hotel.system.rooms.RoomManager;
import com.hotel.system.users.User;
import com.hotel.system.users.UserManager;

import java.util.Scanner;

public class MainMenu {
    private UserManager userManager;
    private Scanner scanner;

    public MainMenu(UserManager userManager) {
        this.userManager = userManager;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu(User user) {
        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. View Rooms");
            System.out.println("2. Book a Room");
            System.out.println("3. Cancel Booking");
            System.out.println("4. View Profile");
            System.out.println("5. Log Out");
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> viewRooms();
                case 2 -> bookRoom(user);
                case 3 -> cancelBooking(user);
                case 4 -> viewProfile(user);
                case 5 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void viewRooms() {
        System.out.println("\nAvailable Rooms:");
        RoomManager roomManager = new RoomManager();

        for (Room room : roomManager.getRooms()) {
            if (room.isAvailable()) {
                System.out.println(room);
            }
        }
    }

    private void bookRoom(User user) {
        System.out.println("\nBook a Room:");
        try {
            System.out.print("Enter room type (e.g., Deluxe): ");
            String roomType = scanner.nextLine();

            System.out.print("Enter start date (YYYY-MM-DD): ");
            String startDate = scanner.nextLine();

            System.out.print("Enter end date (YYYY-MM-DD): ");
            String endDate = scanner.nextLine();

            RoomManager roomManager = new RoomManager();
            BookingManager bookingManager = new BookingManager(roomManager, userManager);

            boolean success = bookingManager.bookRoom(user.getUsername(), roomType, startDate, endDate);
            if (success) {
                System.out.println("Booking completed successfully!");
            } else {
                System.out.println("Failed to book room. Please try again.");
            }
        } catch (Exception e) {
            System.out.println("Error: Invalid input. Please try again.");
        }
    }



    private void cancelBooking(User user) {
        System.out.println("\nCancel Booking:");
        // TODO: Implement logic to find and cancel a booking for the user.
        System.out.println("Feature under construction.");
    }



    private void viewProfile(User user) {
        System.out.println("\nYour Profile:");
        System.out.println(user);
    }
}
