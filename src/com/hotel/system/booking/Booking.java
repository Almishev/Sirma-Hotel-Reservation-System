package com.hotel.system.booking;

import com.hotel.system.rooms.RoomType;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Booking {
    private String username;
    private int roomNumber;
    private RoomType roomType;
    private LocalDate startDate;
    private LocalDate endDate;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Booking(String username, int roomNumber, RoomType roomType, LocalDate startDate, LocalDate endDate) {
        this.username = username;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.startDate = startDate;
        this.endDate = endDate;
    }


    public Booking(String username, int roomNumber, RoomType roomType, String startDate, String endDate) {
        this(username, roomNumber, roomType, LocalDate.parse(startDate, DATE_FORMATTER), LocalDate.parse(endDate, DATE_FORMATTER));
    }

    public String getUsername() {
        return username;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }


    @Override
    public String toString() {
        return "Booking{" +
                "username='" + username + '\'' +
                ", roomNumber=" + roomNumber +
                ", roomType=" + roomType +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

}
