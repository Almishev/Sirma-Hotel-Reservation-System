package com.hotel.system.booking;

import com.hotel.system.rooms.Room;
import com.hotel.system.rooms.RoomManager;
import com.hotel.system.users.User;
import com.hotel.system.users.UserManager;

import java.io.*;
import java.util.*;

public class BookingManager {
    private RoomManager roomManager;
    private UserManager userManager;

    public BookingManager(RoomManager roomManager, UserManager userManager) {
        this.roomManager = roomManager;
        this.userManager = userManager;
    }

    public boolean bookRoom(String username, String roomType, String startDate, String endDate) {

        boolean hasAvailableRooms = roomManager.checkRoomAvailability(roomType, startDate, endDate);

        if (!hasAvailableRooms) {
            System.out.println("No rooms available for the selected type and dates.");
            return false;
        }

        List<Room> availableRooms = roomManager.getAvailableRooms(roomType, startDate, endDate);

        Room selectedRoom = availableRooms.get(0);
        selectedRoom.setAvailable(false);
        roomManager.updateRoomStatus(selectedRoom.getRoomNumber(), false);

        User user = userManager.getUser(username);
        if (user != null) {
            String bookingDetails = selectedRoom.getRoomNumber() + "," + startDate + "," + endDate;
            user.addBooking(bookingDetails);
            userManager.saveUsersToFile();
        }

        Booking booking = new Booking(username, selectedRoom.getRoomNumber(), roomType, startDate, endDate);
        saveBookingToFile(booking);

        System.out.println("Room " + selectedRoom.getRoomNumber() + " booked successfully.");
        return true;
    }


    private List<Room> checkRoomAvailability(String roomType, String startDate, String endDate) {
        return roomManager.getAvailableRooms(roomType, startDate, endDate);
    }

    private void saveBookingToFile(Booking booking) {
        System.out.println("Attempting to save booking: " + booking);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("resources/booking.txt", true))) {
            bw.write(booking.getUsername() + "," +
                    booking.getRoomNumber() + "," +
                    booking.getRoomType() + "," +
                    booking.getStartDate() + "," +
                    booking.getEndDate());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error saving booking: " + e.getMessage());
        }

    }


}
