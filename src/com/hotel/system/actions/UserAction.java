package com.hotel.system.actions;

import com.hotel.system.booking.BookingManager;
import com.hotel.system.rooms.RoomManager;
import com.hotel.system.users.User;

import java.util.Scanner;

public class UserAction extends Action {
    private BookingManager bookingManager;
    private Scanner scanner;

    public UserAction(RoomManager roomManager, BookingManager bookingManager) {
        super(roomManager);
        this.bookingManager = bookingManager;
        this.scanner = new Scanner(System.in);
    }

    public void bookRoom(User user) {
        System.out.println("\nBook a Room:");
        try {
            System.out.print("Enter room type (e.g., Deluxe): ");
            String roomType = scanner.nextLine();

            System.out.print("Enter start date (YYYY-MM-DD): ");
            String startDate = scanner.nextLine();

            System.out.print("Enter end date (YYYY-MM-DD): ");
            String endDate = scanner.nextLine();

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

    public void cancelBooking(User user) {
        System.out.println("\nCancel Booking:");

        var userBookings = bookingManager.getUserBookings(user.getUsername());
        if (userBookings.isEmpty()) {
            System.out.println("You have no bookings to cancel.");
            return;
        }

        System.out.println("Your current bookings:");
        for (int i = 0; i < userBookings.size(); i++) {
            System.out.println((i + 1) + ". " + userBookings.get(i));
        }

        try {
            System.out.print("Enter the number of the booking to cancel: ");
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice < 1 || choice > userBookings.size()) {
                System.out.println("Invalid choice. Please try again.");
                return;
            }

            boolean success = bookingManager.cancelBooking(userBookings.get(choice - 1));
            if (success) {
                System.out.println("Booking canceled successfully!");
            } else {
                System.out.println("Failed to cancel booking. Please try again.");
            }
        } catch (Exception e) {
            System.out.println("Error: Invalid input. Please try again.");
        }
    }

    public void viewProfile(User user) {
        System.out.println("\nYour Profile:");
        System.out.println(user);
    }
}
