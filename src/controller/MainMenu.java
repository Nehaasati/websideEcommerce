package controller;

import java.util.Scanner;
import java.util.logging.Logger;
import Main.Main;


public class MainMenu {
    private static final Logger LOGGER = Logger.getLogger(MainMenu.class.getName());
    private final CustomerController customerController;
    private final AdminController adminController;
    private final GuestMenu guestMenu;  // Add GuestMenu
    private final Scanner scanner;
    //private final Scanner scanner = new Scanner(System.in);

    public MainMenu(CustomerController customerController, AdminController adminController, GuestMenu guestMenu, Scanner scanner) {
        this.customerController = customerController;
        this.adminController = adminController;
        this.guestMenu = guestMenu;
        this.scanner = scanner;
    }

    public void start() {
        while (true) {
            System.out.println("\n🌐 === G I Z M O   G R I D ===");
            System.out.println("1. 👤 Customer");
            System.out.println("2. ⚙️ Admin");
            System.out.println("3. 👀 Guest");
            System.out.println("4. 🚪 Exit");
            System.out.print("🔀 Choose option: ");

            int choice = Main.getIntInput(scanner);
            scanner.nextLine();

            switch (choice) {
                case 1 -> customerController.start(); // Call start() on the instance
                case 2 -> adminController.showAdminMenu();  // Call showAdminMenu() on the instance
                case 3 -> guestMenu.show();  // Call show() on the instance
                case 4 -> {
                    System.out.println("👋 Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("❌ Invalid option");
            }
        }
    }
}


    /*private int getIntInput() {
        try {

            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input. Please enter a number.");
            return -1;
        }*/




