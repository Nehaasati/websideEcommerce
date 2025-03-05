package model;

import java.util.logging.Logger;

public class Customer {
    private static final Logger logger = Logger.getLogger(Customer.class.getName());

    private int customerId;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String password;


    public Customer(int customerId, String name, String email, String phone, String address, String password) {

        if (customerId <= 0) {
            logger.severe("Attempt to create customer with invalid customer ID: " + customerId);
            throw new IllegalArgumentException("Invalid customer ID");
        }

        if (name == null || name.trim().isEmpty()) {
            logger.warning("Attempt to create customer with empty name");
            throw new IllegalArgumentException("Name cannot be empty");
        }

        if (email == null || !email.contains("@")) {
            logger.warning("Invalid email format: " + email);
            throw new IllegalArgumentException("Invalid email address");
        }

        // Trim password before validation
        if (password != null) {
            password = password.trim();
        }

        this.customerId = customerId;
        this.name = name.trim();
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.setPassword(password);

        logger.info("Created customer object:" + email);
    }

    // getters
    public int getCustomerId() {return customerId;}
    public String getName() {return name;}
    public String getEmail() {return email;}
    public String getPhone() {return phone;}
    public String getAddress() {return address;}
    public String getPassword() {return password;}

    //setters with validation


    public void setName(String newName) {
        if (newName == null || newName.trim().isEmpty()) {
            logger.warning("Attempt to set invalid name: " + newName);
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = newName;
        logger.info("Updated name for customer: " + customerId);
    }

    public void setEmail(String newEmail) {
        if (newEmail == null || !newEmail.contains("@")) {
            logger.warning("Attempt to set invalid email: " + newEmail);
            throw new IllegalArgumentException("Invalid email format");
        }
        this.email = newEmail;
        logger.info("Updated email for customer: " + customerId);
    }

    public void setPhone(String newPhone) {
        if (newPhone == null || newPhone.trim().isEmpty()) {
            logger.warning("Attempt to set invalid phone: " + newPhone);
            throw new IllegalArgumentException("Phone cannot be empty");
        }
        this.phone = newPhone;
        logger.info("Updated phone for customer: " + customerId);
    }

    public void setAddress(String newAddress) {
        if (newAddress == null || newAddress.trim().isEmpty()) {
            logger.warning("Attempt to set invalid address: " + newAddress);
            throw new IllegalArgumentException("Address cannot be empty");
        }
        this.address = newAddress;
        logger.info("Updated address for customer: " + customerId);
    }

    public void setPassword(String newPassword) {
        if (newPassword == null || newPassword.trim().isEmpty()) {
            logger.warning("Attempt to set empty password for customer: " + customerId);
            throw new IllegalArgumentException("Password cannot be empty");
        }
        this.password = newPassword;
        /*if (newPassword.length() < 8) {
            logger.warning("Password too short for customer: " + customerId);
            throw new IllegalArgumentException("Password must be at least 8 characters");
        }
        this.password = newPassword;
        logger.info("Updated password for customer: " + customerId);*/
    }


    @Override
    public String toString() {
        return "Customer [ID: " + customerId +
                ", Name: " + name +
                ", Email: " + email +
                ", Phone: " + phone +
                ", Address: " + address + "]";
    }
}