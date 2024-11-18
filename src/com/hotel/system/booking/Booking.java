package com.hotel.system.booking;

public class Booking {
    private String username;
    private int roomNumber;
    private String roomType;
    private String startDate;
    private String endDate;

    public Booking(String username, int roomNumber, String roomType, String startDate, String endDate) {
        this.username = username;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getUsername() {
        return username;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public boolean overlapsWith(String start, String end) {
        return !(end.compareTo(startDate) < 0 || start.compareTo(endDate) > 0);
    }

    @Override
    public String toString() {
        return "Booking{" +
                "username='" + username + '\'' +
                ", roomNumber='" + roomNumber + '\'' +
                ", roomType='" + roomType + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
