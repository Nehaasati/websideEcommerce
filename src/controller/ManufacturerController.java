package controller;

import model.Manufacturer;
import service.ManufacturerService;

import java.util.List;

public class ManufacturerController {
    private final ManufacturerService manufacturerService;

    public ManufacturerController() {
        this.manufacturerService = new ManufacturerService();
    }

    public void displayManufacturers() {

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
                System.out.printf("| %-13d | %-23s |\n",
                        manufacturer.getManufacturerId(),
                        manufacturer.getName());
            }

            System.out.println("+---------------+-------------------------+");
            System.out.println(manufacturers.size() + " manufacturer(s) found");
        }
    }
}