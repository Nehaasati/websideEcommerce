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
    public List<Manufacturer> getAllManufacturers() throws SQLException, IllegalArgumentException {
        // Fetch from repository (propagates SQLException)
        List<Manufacturer> manufacturers = manufacturerRepository.getAllManufacturers();

        // Handle empty result
        if (manufacturers.isEmpty()) {
            throw new IllegalArgumentException("No manufacturers found in the database.");
        }

        return manufacturers;
    }

    public Manufacturer getManufacturerById(int id) throws SQLException, IllegalArgumentException {
        // Validate input
        if (id <= 0) {
            throw new IllegalArgumentException("Manufacturer ID must be a positive integer.");
        }

        // Fetch from repository (propagates SQLException)
        Manufacturer manufacturer = manufacturerRepository.getManufacturerById(id);

        // Handle not found
        if (manufacturer == null) {
            throw new IllegalArgumentException("Manufacturer with ID " + id + " does not exist.");
        }

        return manufacturer;
    }
}
