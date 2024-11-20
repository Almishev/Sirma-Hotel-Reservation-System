package com.hotel.system.rooms;

import com.hotel.system.booking.Booking;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Room {
    private int roomNumber;
    private RoomType roomType;
    private double pricePerNight;
    private double cancellationFee;
    private boolean available;
    private List<Booking> bookings;

    public Room(int roomNumber, RoomType type, double pricePerNight, double cancellationFee, boolean available) {
        this.roomNumber = roomNumber;
        this.roomType = type;
        this.pricePerNight = pricePerNight;
        this.cancellationFee = cancellationFee;
        this.available = available;
        this.bookings = new ArrayList<>();
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public RoomType getType() {
        return roomType;
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

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void updateStatus(boolean isAvailable) {
        this.available = isAvailable;
    }




    public boolean isAvailableForDates(LocalDate startDate, LocalDate endDate) {
        for (Booking booking : bookings) {
            if (!(endDate.isBefore(booking.getStartDate()) || startDate.isAfter(booking.getEndDate()))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "Room " + roomNumber + " [" + roomType.toString() + "] - " + available +
                ", Price: " + pricePerNight + " BGN, Cancellation Fee: " + cancellationFee + " BGN";
    }
}
