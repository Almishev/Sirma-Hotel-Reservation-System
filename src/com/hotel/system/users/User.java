package com.hotel.system.users;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password;
    private String role;
    private List<String> bookingHistory;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = "guest";
        this.bookingHistory = new ArrayList<>();

    }

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.bookingHistory = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public void addBooking(String bookingDetails) {
        bookingHistory.add(bookingDetails);
    }

    public List<String> getBookingHistory() {
        return bookingHistory;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", bookingHistory=" + bookingHistory +
                '}';
    }
}
