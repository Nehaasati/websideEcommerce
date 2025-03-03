package service;

import model.Customer;

public interface CustomerService {
    Customer registerCustomer(String name, String email, String phone,
                              String address, String password);
    Customer login(String email, String password);
    Customer getCustomer(int customerId);
    void updateCustomer(Customer customer);
    boolean validateCredentials(String email, String password);
}