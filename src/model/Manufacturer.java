package model;

public class Manufacturer {
    private int manufacturerId;
    private String name;


    public Manufacturer() {
    }

    public Manufacturer(int manufacturerId, String name) {
        if (manufacturerId < 0) {
            throw new IllegalArgumentException(
                    "ManufacturerId must be greater than 0."
            );
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("ManufacturerName cannot be null or empty.");
        }
        this.manufacturerId = manufacturerId;
        this.name = name;
    }


    public int getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(int manufacturerId) {
        if (manufacturerId < 0) {
            throw new IllegalArgumentException("ManufacturerId must be greater than 0.");
        }
        this.manufacturerId = manufacturerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("ManufacturerName cannot be null or empty.");
        }
        this.name = name;
    }

    @Override
    public String toString() {
        return "Manufacturer [ID: " + manufacturerId + ", Name: " + name + "]";
    }
}