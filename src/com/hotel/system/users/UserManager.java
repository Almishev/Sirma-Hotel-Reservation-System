package com.hotel.system.users;

import com.hotel.system.Security.AESUtils;
import com.hotel.system.interfaces.LoadAble;
import com.hotel.system.interfaces.SaveAble;

import javax.crypto.SecretKey;
import java.io.*;
import java.util.*;

public class UserManager implements LoadAble, SaveAble {
    private final SecretKey secretKey;
    private Map<String, User> users;

    public UserManager() {
        this.users = new HashMap<>();
        this.secretKey = loadOrGenerateKey();
        loadDataFromFile();
    }

    private SecretKey loadOrGenerateKey() {
        try {

            File keyFile = new File("resources/secretKey.txt");
            if (keyFile.exists()) {
                try (BufferedReader br = new BufferedReader(new FileReader(keyFile))) {
                    String keyString = br.readLine();
                    return AESUtils.getKeyFromString(keyString);
                }
            } else {

                SecretKey generatedKey = AESUtils.generateKey();
                try (PrintWriter pw = new PrintWriter(new FileWriter(keyFile))) {
                    pw.println(AESUtils.keyToString(generatedKey));
                }
                return generatedKey;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error loading or generating key: " + e.getMessage());
        }
    }

       public void loadDataFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("resources/users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String username = parts[0];
                String encryptedPassword = parts[1];
                String role = (parts.length > 2 && !parts[2].isEmpty()) ? parts[2] : "guest";

                User user = new User(username, encryptedPassword, role);
                if (parts.length > 3) {
                    user.getBookingHistory().addAll(Arrays.asList(parts).subList(3, parts.length));
                }
                users.put(username, user);
            }
        } catch (FileNotFoundException e) {
            System.out.println("No users file found. Starting fresh.");
        } catch (Exception e) {
            System.out.println("Error reading users file: " + e.getMessage());
        }
    }

    public boolean registerUser(String username, String password, String role) {
        if (users.containsKey(username)) {
            return false;
        }

        User newUser = new User(username, password, role);
        users.put(username, newUser);
        saveDataToFile();
        return true;
    }

    public User logIn(String username, String password) {
        User user = users.get(username);
        if (user != null && authenticateUser(username, password)) {
            return user;
        }
        return null;
    }

    private boolean authenticateUser(String username, String password) {
        User user = users.get(username);
        if (user != null) {
            String encryptedPassword = user.getPassword();
            String decryptedPassword = null;
            try {
                decryptedPassword = AESUtils.decrypt(encryptedPassword, secretKey);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return decryptedPassword.equals(password);
        }
        return false;
    }

    @Override
    public void saveDataToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("resources/users.txt"))) {
            for (User user : users.values()) {
                String encryptedPassword = AESUtils.encrypt(user.getPassword(), secretKey);
                pw.print(user.getUsername() + "," + encryptedPassword + "," + user.getRole() + ",");
                pw.println(String.join(",", user.getBookingHistory()));
            }
        } catch (Exception e) {
            System.out.println("Error saving users file: " + e.getMessage());
        }
    }

    public Collection<User> getAllUsers() {
        return users.values();
    }

    public User getUser(String username) {
        return users.get(username);
    }

    public void addBookingToHistory(String username, String bookingDetails) throws Exception {
        User user = users.get(username);
        if (user != null) {
            user.getBookingHistory().add(bookingDetails);
            updateUser(user); // Update the user information in the file
        } else {
            System.out.println("The user is not found.");
        }
    }

    private void updateUser(User user) throws Exception {
        try {
            File tempFile = new File("resources/temp_users.txt");
            try (PrintWriter pw = new PrintWriter(new FileWriter(tempFile))) {
                for (User u : users.values()) {
                    String encryptedPassword = AESUtils.encrypt(u.getPassword(), secretKey);
                    pw.print(u.getUsername() + "," + encryptedPassword + "," + u.getRole() + ",");
                    pw.println(String.join(",", u.getBookingHistory()));
                }
            }
            if (!tempFile.renameTo(new File("resources/users.txt"))) {
                System.out.println("Error replacing users file.");
            }
        } catch (IOException e) {
            System.out.println("Error updating user information: " + e.getMessage());
        }
    }
}