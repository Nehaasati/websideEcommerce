/*package service;

import java.util.List;

import repository.CustomerRepository;
import model.Customer;

public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService() {
        this.customerRepository = new CustomerRepository();
    }

    public void showAllCustomers() {
        List<Customer> customers = customerRepository.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            for (Customer customer : customers) {
                System.out.println("ID: " + customer.getCustomer_id());
                System.out.println("Name: " + customer.getName());
                System.out.println("Email: " + customer.getEmail());
                System.out.println("Phone: " + customer.getPhone());
                System.out.println("Address: " + customer.getAddress());
                System.out.println("-----------------");
            }
        }
    }

    public Customer getCustomerById(int id) {
        Customer customer = customerRepository.getCustomerById(id);
        if (customer != null) {
            System.out.println("Customer found: " + customer.getName());
        } else {
            System.out.println("Customer not found.");
        }
        return customer;
    }
    public void addCustomer(String name,String email, String phone,String address,String password) {

        Customer customer1 = new Customer(0,name,email,phone,address,password);
        boolean success = customerRepository.addNewCustomer(customer1);
        if (success) {
            System.out.println("Customer added sucessfully");
        } else {
            System.out.println("Customer not added");
        }
    }
    public boolean UpdateCustomer(Customer customer){
        boolean success = customerRepository.updateCustomer(customer);
        if(success){
            System.out.println("Customer updated successfilly");
        }
        else{
            System.out.println("Customer not updatewd");
        }
        return success;
    }

    public  boolean deleteCustomer(int id ){
        boolean sucess  = customerRepository.deleteCustomer(id);
        if(sucess){
            System.out.println("Customer deletes successfully");
        } else {
            System.out.println("Customer not deleted");
        }
        return sucess;
    }

    public Customer getCustomerByEmail(String email){
        Customer customer = customerRepository.getCustomerByEmail(email);
        if (customer != null) {
            System.out.println("Customer found: " + customer.getName());
        } else {
            System.out.println("Customer not found.");
        }
        return customer;
    }

}





/**
 * Här kan man lägga till fler metoder som t.ex:
 * - getCustomerById
 * - addNewCustomer
 * - updateCustomer
 * - deleteCustomer
 * - findCustomerByEmail
 */