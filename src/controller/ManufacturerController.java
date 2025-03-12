package controller;

import model.Manufacturer;
import service.ManufacturerService;

import java.util.List;
import java.sql.SQLException;
import java.util.Scanner;


public class ManufacturerController {
    private final ManufacturerService manufacturerService;
    private final Scanner scanner;

    public ManufacturerController() {
        this.manufacturerService = new ManufacturerService();
        this.scanner = new Scanner(System.in);
    }

    // Main.Main menu entry point
    public void start() {
        while (true) {
            System.out.println("\n--- Manufacturer Management ---");
            System.out.println("1. List all Manufacturers");
            System.out.println("2. Get Manufacturer by Id");
            System.out.println("3. Return to the menu");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    displayManufacturers();
                    break;
                case "2":
                    // Get the ID from the user here
                    System.out.print("Enter Manufacturer ID: ");
                    try {
                        int id = Integer.parseInt(scanner.nextLine());  // Get ID from user
                        displayManufacturerById(id);  // Calling displayManufacturerById with the ID
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid ID.");
                    }
                    break;
                case "3":
                    System.out.println("Returning to main menu...");
                    return;
                case "4":
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    public void displayManufacturers() {
        try {
            List<Manufacturer> manufacturers = manufacturerService.getAllManufacturers();

            if (manufacturers.isEmpty()) {
                System.out.println("No manufacturers found");
            } else {
                // Display as a table with formatting
                System.out.println("+---------------+-------------------------+");
                System.out.println("| ID            | Name                    |");
                System.out.println("+---------------+-------------------------+");

                for (Manufacturer manufacturer : manufacturers) {
                    // Format each row with fixed width columns
                    System.out.printf("| %-13d | %-20s |\n",
                            manufacturer.getManufacturerId(),
                            manufacturer.getName());
                }

                System.out.println("+---------------+-------------------------+");
                System.out.println(manufacturers.size() + " manufacturer(s) found");
            }
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Display manufacturer by ID with consistent formatting
   public void displayManufacturerById(int id) {
        try {
            Manufacturer manufacturer = manufacturerService.getManufacturerById(id);

            if (manufacturer == null) {
                System.out.println("No manufacturer found with ID: " + id);
            } else {
                System.out.println("+---------------+-------------------------+");
                System.out.println("| ID            | Name                    |");
                System.out.println("+---------------+-------------------------+");

                System.out.printf("| %-13d | %-23s |\n",
                        manufacturer.getManufacturerId(),
                        manufacturer.getName());

                System.out.println("+---------------+-------------------------+");
                System.out.println("1 manufacturer found");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }
}