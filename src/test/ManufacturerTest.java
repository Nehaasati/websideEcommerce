package test;

import model.Manufacturer;

import repository.ManufacturerRepository;

import java.util.List;

public class ManufacturerTest {
    public static void main(String[] args) {
        ManufacturerRepository repository = new ManufacturerRepository();

        System.out.println("\n🔍 **Testing ManufacturerRepository Error Handling** 🔍");

        // ✅ Test Retrieving Manufacturers
        testGetAllManufacturers(repository);
        //testGetManufacturerById(repository, 1);
    }

    public static void testGetAllManufacturers(ManufacturerRepository repository) {  // ✅ Fixed method signature
        System.out.println("\n➡️ Fetching all manufacturers...");
        try {
            List<Manufacturer> manufacturers = repository.getAllManufacturers();
            if (manufacturers.isEmpty()) {
                System.out.println("⚠️ No manufacturers found.");
            } else {
                System.out.println("+-----------------------+");
                System.out.println("|  ID  |     Name      |");
                System.out.println("+-----------------------+");
                for (Manufacturer manufacturer : manufacturers) {
                    System.out.printf("|  %-3d | %-12s |\n",
                            manufacturer.getManufacturerId(), manufacturer.getName());
                }
                System.out.println("+-----------------------+");
            }
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

   /* public static void testGetManufacturerById(ManufacturerRepository repository, int id) {
        System.out.println("\n➡️ Fetching Manufacturer with ID: " + id);
        try {
            Manufacturer manufacturer = repository.getManufacturerById(id);
            if (manufacturer == null) {
                System.out.println("⚠️ Manufacturer Not Found.");
            } else {
                System.out.println("✅ Manufacturer Found: " + manufacturer);
            }
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }*/
}

