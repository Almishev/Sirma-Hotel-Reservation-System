import com.hotel.system.menus.AdminMenu;
import com.hotel.system.menus.MainMenu;
import com.hotel.system.rooms.RoomManager;
import com.hotel.system.rooms.RoomTypeManager;
import com.hotel.system.users.User;
import com.hotel.system.users.UserManager;

import java.util.Scanner;

public class Main {
    private static UserManager userManager = new UserManager();
    private static Scanner scanner = new Scanner(System.in);
    private static RoomTypeManager roomTypeManager = new RoomTypeManager();
    private static RoomManager roomManager = new RoomManager();
    private static User currentUser = null;

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nWelcome to the Hotel Management System!");
            if (currentUser == null) {
                System.out.println("1. Register");
                System.out.println("2. Log In");
                System.out.println("0. Exit");
                System.out.print("Choose an option: ");
                int choice = Integer.parseInt(scanner.nextLine());

                if (choice == 0) {
                    System.out.println("Goodbye!");
                    break;
                } else if (choice == 1) {
                    registerUser();
                } else if (choice == 2) {
                    logInUser();
                } else {
                    System.out.println("Invalid option. Please try again.");
                }
            } else {
                boolean stayLoggedIn;
                if ("admin".equalsIgnoreCase(currentUser.getRole())) {
                    AdminMenu adminMenu = new AdminMenu(userManager,roomManager);
                    stayLoggedIn = adminMenu.showMenu(currentUser);
                } else {
                    MainMenu mainMenu = new MainMenu(userManager,roomTypeManager);
                    stayLoggedIn = mainMenu.showMenu(currentUser);
                }


                if (!stayLoggedIn) {
                    currentUser = null;
                }
            }
        }
    }


    private static void registerUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (userManager.registerUser(username, password, "guest")) {
            System.out.println("Registration successful! You can now log in.");
        } else {
            System.out.println("Username already exists. Try again.");
        }
    }


    private static void logInUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = userManager.logIn(username, password);
        if (user != null) {
            currentUser = user;
            System.out.println("Welcome, " + currentUser.getUsername() + "!");
        } else {
            System.out.println("Invalid username or password.");
        }
    }
}
