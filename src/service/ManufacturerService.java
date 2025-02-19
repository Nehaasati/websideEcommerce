package service;

import Repository.ManufacturerRepository;
import model.Manufacturers;

import java.util.List;

public class ManufacturerService {
    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerService() {

        this.manufacturerRepository = new ManufacturerRepository();
    }

    public List<Manufacturers> getAllManufacturer() {

        return manufacturerRepository.getAllManufacturers();
    }
}

