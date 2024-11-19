package com.hotel.system.booking;

import com.hotel.system.rooms.Room;
import com.hotel.system.rooms.RoomManager;
import com.hotel.system.rooms.RoomType;
import com.hotel.system.rooms.RoomTypeManager;
import com.hotel.system.users.User;
import com.hotel.system.users.UserManager;

import java.io.*;
import java.util.*;

public class BookingManager {
    private RoomManager roomManager;
    private UserManager userManager;

    private RoomTypeManager roomTypeManager;
    private List<Booking> bookings;

    public BookingManager(RoomManager roomManager, UserManager userManager,RoomTypeManager roomTypeManager) {
        this.roomManager = roomManager;
        this.roomTypeManager = roomTypeManager;
        this.userManager = userManager;
        this.bookings = loadBookingsFromFile();
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


    public List<Booking> getUserBookings(String username) {
        return bookings.stream()
                .filter(booking -> booking.getUsername().equalsIgnoreCase(username))
                .toList();
    }



    public boolean cancelBooking(Booking bookingToCancel) {

        boolean removed = bookings.removeIf(booking ->
                booking.getUsername().equals(bookingToCancel.getUsername()) &&
                        booking.getRoomNumber() == bookingToCancel.getRoomNumber() &&
                        booking.getStartDate().equals(bookingToCancel.getStartDate()) &&
                        booking.getEndDate().equals(bookingToCancel.getEndDate())
        );

        if (removed) {
            saveBookingsToFile();
            User user = userManager.getUser(bookingToCancel.getUsername());
            if (user != null) {

                userManager.saveUsersToFile();
            }

            return true;
        }

        return false;
    }


    private void saveBookingsToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("resources/booking.txt"))) {
            for (Booking booking : bookings) {
                bw.write(booking.getUsername() + "," +
                        booking.getRoomNumber() + "," +
                        booking.getRoomType() + "," +
                        booking.getStartDate() + "," +
                        booking.getEndDate());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving bookings: " + e.getMessage());
        }
    }


    public boolean bookRoom(String username, String roomType, String startDate, String endDate) {
        boolean hasAvailableRooms = roomManager.checkRoomAvailability(roomType, startDate, endDate);
        if (!hasAvailableRooms) {
            System.out.println("No rooms available for the selected type and dates.");
            return false;}
        List<Room> availableRooms = roomManager.getAvailableRooms(roomType, startDate, endDate);
        List<Booking> existingBookings = loadBookingsFromFile();
        for (Room room : availableRooms) {
            boolean isRoomBooked = existingBookings.stream().anyMatch(b ->
                    b.getRoomNumber() == room.getRoomNumber() &&
                            !(endDate.compareTo(b.getStartDate()) <= 0 || startDate.compareTo(b.getEndDate()) >= 0));
            if (!isRoomBooked) {
                room.setAvailable(false);
                roomManager.updateRoomStatus(room.getRoomNumber(), false);
                User user = userManager.getUser(username);
                if (user != null) {
                    String bookingDetails = room.getRoomNumber() + "," + startDate + "," + endDate;
                    user.addBooking(bookingDetails);
                    userManager.saveUsersToFile();}
                Booking booking = new Booking(username, room.getRoomNumber(), room.getType(), startDate, endDate);
                saveBookingToFile(booking);
                System.out.println("Room " + room.getRoomNumber() + " booked successfully.");
                return true;
            }
        }
        System.out.println("No rooms available for the selected type and dates.");
        return false;
    }



    private List<Booking> loadBookingsFromFile() {
        List<Booking> bookings = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("resources/booking.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String username = parts[0];
                    int roomNumber = Integer.parseInt(parts[1]);
                    String roomTypeName = parts[2];

                    RoomType roomType = roomTypeManager.findRoomTypeByName(roomTypeName);

                    if (roomType != null) {
                        String startDate = parts[3];
                        String endDate = parts[4];
                        bookings.add(new Booking(username, roomNumber, roomType, startDate, endDate));
                    } else {
                        System.out.println("Invalid room type: " + roomTypeName);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading bookings: " + e.getMessage());
        }
        return bookings;
    }

    public List<Booking> getAllBookings() {
        return bookings;
    }


}
