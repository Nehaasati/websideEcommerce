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
        List<Manufacturer> manufacturers = manufacturerService.getAllManufacturer();
        if (manufacturers.isEmpty()) {
            System.out.println("No manufacturers found");
        }else{
            for (Manufacturer manufacturer : manufacturers) {
                System.out.println(manufacturer);
            }
        }
    }
}
