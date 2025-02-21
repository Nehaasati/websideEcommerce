package service;

import repository.ManufacturerRepository;
import model.Manufacturer;

import java.util.List;

public class ManufacturerService {
    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerService() {

        this.manufacturerRepository = new ManufacturerRepository();
    }

    public List<Manufacturer> getAllManufacturer() {

        return manufacturerRepository.getAllManufacturers();
    }
}

