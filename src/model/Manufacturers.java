package model;

public class Manufacturers {
    private int manufacturerId;
    private String name;


    public Manufacturers(int manufacturerId, String name) {
        this.manufacturerId = manufacturerId;
        this.name = name;
    }

    public int getManufacturerId() {
        return manufacturerId;
    }
    public void setManufacturerId(int manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Manufacturer [ID: " + manufacturerId + ", Name: " + name + "]";
    }
}

