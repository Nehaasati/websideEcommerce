package controller;

import model.Manufacturers;
import service.ManufacturerService;

import java.util.List;

public class ManufacturerController {
    private final ManufacturerService manufacturerService;

    public ManufacturerController() {
        this.manufacturerService = new ManufacturerService();
    }

    public void displayManufacturers() {
        List<Manufacturers> manufacturers = manufacturerService.getAllManufacturer();
        if (manufacturers.isEmpty()) {
            System.out.println("No manufacturers found");
        }else{
            for (Manufacturers manufacturer : manufacturers) {
                System.out.println(manufacturer);
            }
        }
    }
}
