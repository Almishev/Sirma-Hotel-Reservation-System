package com.hotel.system.users;


import java.io.*;
import java.util.*;

public class UserManager {
    private final CaesarCipher cipher = new CaesarCipher(3);
    private Map<String, User> users;


    public UserManager() {
        this.users = new HashMap<>();
        loadUsersFromFile();
    }


    private void loadUsersFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("resources/users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String username = parts[0];
                String decryptedPassword = cipher.decrypt(parts[1]);

                String role = (parts.length > 2 && !parts[2].isEmpty()) ? parts[2] : "guest";

                User user = new User(username, decryptedPassword, role);
                if (parts.length > 3) {
                    user.getBookingHistory().addAll(Arrays.asList(parts).subList(3, parts.length));
                }
                users.put(username, user);
            }
        } catch (FileNotFoundException e) {
            System.out.println("No users file found. Starting fresh.");
        } catch (IOException e) {
            System.out.println("Error reading users file: " + e.getMessage());
        }
    }


    public boolean registerUser(String username, String password, String role) {
        if (users.containsKey(username)) {
            return false;
        }

        User newUser = new User(username, password);
        users.put(username, newUser);
        saveUsersToFile();

        return true;
    }

    public User logIn(String username, String password) {
        User user = users.get(username);
        if (user != null && user.checkPassword(password)) {
            return user;
        }
        return null;
    }

    public void saveUsersToFile() {

        try (PrintWriter pw = new PrintWriter(new FileWriter("resources/users.txt"))) {
            for (User user : users.values()) {
                String encryptedPassword = cipher.encrypt(user.getPassword());
                pw.print(user.getUsername() + "," + encryptedPassword + ","+user.getRole()+",");
                pw.println(String.join(",", user.getBookingHistory()));
            }
        } catch (IOException e) {
            System.out.println("Error saving users file: " + e.getMessage());
        }
    }

    public Collection<User> getAllUsers() {
        return users.values();
    }

    public User getUser(String username) {
        return users.get(username);
    }

}
