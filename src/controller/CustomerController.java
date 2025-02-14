package controller;

import java.util.Scanner;

import model.Customer;
import service.CustomerService;

public class CustomerController {
    private final CustomerService customerService;
    private final Scanner scanner;

    public CustomerController() {
        this.customerService = new CustomerService();
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        while (true) {
            System.out.println("\n=== Customer Management ===");
            System.out.println("1. View All Customers");
            System.out.println("2. Search Customer by ID");
            System.out.println("3. Add Customer");
            System.out.println("4.Update Customer");
            System.out.println("5.Delete Customer");
            System.out.println("6. Search Customer by email");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    customerService.showAllCustomers();
                    break;
                case 2:
                    System.out.print("Enter Customer ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    Customer customer = customerService.getCustomerById(id);

                    if (customer != null) {
                        System.out.println("\nCustomer Details:");
                        System.out.println("ID: " + customer.getCustomer_id());
                        System.out.println("Name: " + customer.getName());
                        System.out.println("Email: " + customer.getEmail());
                        System.out.println("Phone: " + customer.getPhone());
                        System.out.println("Address: " + customer.getAddress());
                    } else {
                        System.out.println("Customer not found.");
                    }
                    break;
                case 3:
                    System.out.println("\nEnter customer name");
                    String name = scanner.nextLine();
                    System.out.println("\nEnter customer email");
                    String email = scanner.nextLine();
                    System.out.println("\nEnter customer phone");
                    String phone = scanner.nextLine();
                    System.out.println("\nEnter customer address");
                    String address = scanner.nextLine();
                    System.out.println("\nEnter customer password");
                    String password = scanner.nextLine();

                    CustomerService customerservise = new CustomerService();
                    customerservise.addCustomer(name,email,phone,address,password);
                    break;

                case 4 :
                    System.out.println("\nEnter customer Id to update");
                    int updateId = scanner.nextInt();
                    scanner.nextLine();//cusumer

                    Customer existingCustomer = customerService.getCustomerById(updateId);
                    if (existingCustomer == null){
                        System.out.println("\nCustomer not found");

                    }
                    System.out.println("\nEnter customer name:(leave empty for existing customer:"+ existingCustomer.getName()+"):");
                    String newName = scanner.nextLine();
                    if (newName.isEmpty()) existingCustomer.setName(newName);

                    System.out.println("\nEnter customer email:(leave empty for exixting customer:" + existingCustomer.getEmail()+ "):");
                    String newEmail = scanner.nextLine();
                    if(newEmail.isEmpty()) existingCustomer.setEmail(newEmail);

                    System.out.println("\nEnter customer phone :(leave empty for existing customer: "+existingCustomer.getPhone()+ "):");
                    String newPhone = scanner.nextLine();
                    if(newPhone.isEmpty())existingCustomer.setPhone(newPhone);

                    System.out.println("\nEnter customer address :(leave empty for existing customer:"+existingCustomer.getAddress()+ "):");
                    String newAddress = scanner.nextLine();
                    if(newAddress.isEmpty())existingCustomer.setAddress(newAddress);

                    System.out.println("\nEnter customer password:(leave empty for existing customer)");
                    String newPassword = scanner.nextLine();
                    if(newPhone.isEmpty())existingCustomer.setPassword(newPassword);
                    Customer updatedCustomer = new Customer(updateId,newName,newPhone,newPassword,newEmail,newAddress);
                    if(customerService.UpdateCustomer(updatedCustomer)){
                        System.out.println("\n Customer updated sucessfully");
                    }else{
                        System.out.println("\n Customer updated failed");
                    }
                    break;
                case 5:
                    System.out.println("\nEnter customer Id to delete");
                    int deletedId =scanner.nextInt();
                    scanner.nextLine();
                    boolean deleted = customerService.deleteCustomer(deletedId);
                    if(deleted){
                        System.out.println("\nCustomer deleted sucessfully");
                    }else {
                        System.out.println("\nCustomer deleted failed");
                    }
                    break;
                case 6:
                    System.out.println("\nEnter customer find byEmail");
                    String findEmail =scanner.nextLine();
                    scanner.nextLine();

                    Customer foundCustomer = customerService.getCustomerByEmail(findEmail);
                    if(foundCustomer!= null){
                        System.out.println("\n customer details:");
                        System.out.println("customer  id:"+foundCustomer.getCustomer_id());
                        System.out.println("customer name:"+foundCustomer.getName());
                        System.out.println("customer email:"+foundCustomer.getEmail());
                        System.out.println("customer phone:"+foundCustomer.getPhone());
                        System.out.println("customer address:"+foundCustomer.getAddress());
                    }
                    else {
                        System.out.println("\n customer not found");
                    }
                    break;



                case 0:
                    System.out.println("Exiting customer management...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}


