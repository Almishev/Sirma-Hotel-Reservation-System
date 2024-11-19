package com.hotel.system.rooms;

import com.hotel.system.booking.Booking;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private int roomNumber;
    private String type;
    private double pricePerNight;
    private double cancellationFee;
    private boolean available;
    private List<Booking> bookings;

    public Room(int roomNumber, String type, double pricePerNight, double cancellationFee, boolean available) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.pricePerNight = pricePerNight;
        this.cancellationFee = cancellationFee;
        this.available = available;
        this.bookings = new ArrayList<>();
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getType() {
        return type;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public double getCancellationFee() {
        return cancellationFee;
    }

    public boolean isAvailable() {
        return available;
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }
    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void updateStatus(boolean isAvailable) {
        this.available = isAvailable;
    }




    public boolean isAvailableForDates(String startDate, String endDate) {
        for (Booking booking : bookings) {
            if (booking.overlapsWith(startDate, endDate)) {
                return false;
            }
        }
        return true;
    }


    @Override
    public String toString() {
        return "Room " + roomNumber + " [" + type + "] - " + available +
                ", Price: " + pricePerNight + " BGN, Cancellation Fee: " + cancellationFee + " BGN";
    }
}
