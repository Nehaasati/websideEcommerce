package service;

import repository.ManufacturerRepository;
import model.Manufacturer;

import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class ManufacturerService {
    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerService() {
        this.manufacturerRepository = new ManufacturerRepository();
    }

    // Either handle the SQLException
    public List<Manufacturer> getAllManufacturers() {
        try {
            return manufacturerRepository.getAllManufacturers();
        } catch (SQLException e) {
            System.err.println("Error retrieving manufacturers: " + e.getMessage());
            // Return empty list or handle error as appropriate for your application
            return new ArrayList<>();
        }
    }


}