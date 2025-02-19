package test;

import model.Manufacturers;

public class ManufacturerTestValidation {
    public class ManufacturersValidationManualTest {
        public static void main(String[] args) {
            testManufacturerIdZero();
            testManufacturerIdNegative();
            testNullName();
            testEmptyName();
            testValidManufacturer();
        }

        private static void testManufacturerIdZero() {
            try {
                new Manufacturers(0, "Valid Name");
                System.out.println("testManufacturerIdZero: Failed - no exception thrown.");
            } catch (IllegalArgumentException e) {
                System.out.println("testManufacturerIdZero: Passed - " + e.getMessage());
            }
        }

        private static void testManufacturerIdNegative() {
            try {
                new Manufacturers(-1, "Valid Name");
                System.out.println("testManufacturerIdNegative: Failed - no exception thrown.");
            } catch (IllegalArgumentException e) {
                System.out.println("testManufacturerIdNegative: Passed - " + e.getMessage());
            }
        }

        private static void testNullName() {
            try {
                new Manufacturers(1, null);
                System.out.println("testNullName: Failed - no exception thrown.");
            } catch (IllegalArgumentException e) {
                System.out.println("testNullName: Passed - " + e.getMessage());
            }
        }

        private static void testEmptyName() {
            try {
                new Manufacturers(1, "");
                System.out.println("testEmptyName: Failed - no exception thrown.");
            } catch (IllegalArgumentException e) {
                System.out.println("testEmptyName: Passed - " + e.getMessage());
            }
        }

        private static void testValidManufacturer() {
            try {
                Manufacturers manufacturer = new Manufacturers(1, "Valid Name");
                System.out.println("testValidManufacturer: Passed - Manufacturer created: " + manufacturer);
            } catch (IllegalArgumentException e) {
                System.out.println("testValidManufacturer: Failed - Exception thrown: " + e.getMessage());
            }
        }
    }
}


