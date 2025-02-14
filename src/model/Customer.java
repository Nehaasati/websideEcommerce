package model;

public class Customer {

    // Privata fält för att uppnå inkapsling
    private int customer_id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String password;
    /**
     * Konstruktor för att skapa en ny Customer
     * Tar emot all nödvändig information för en kund
     *
     */
    public Customer(int customer_id, String name, String email, String phone, String address, String password) {
        this.customer_id = customer_id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.password =password;
    }

    // Getters och setters för alla fält
    public int getCustomer_id() {
        return customer_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String Name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress(){
        return address;
    }
    public void setAddress(String address){
        this.address =address;
    }
    public String getPassword(){
        return password;

    }
    public void setPassword(String password){
        this.password = password;
    }

    /**
     * toString-metod för att få en läsbar representation av kunden
     * Användbar vid utskrift eller debugging
     */
    @Override
    public String toString() {
        return "Customer{" +
                "id=" + customer_id +
                ", firstName='" + name + '\'' +
                ", lastName='" + email + '\'' +
                ", email='" + phone + '\'' +
                ",address='"  + address + '\''+
                ",password='" + password + '\''+
                '}';
    }
}

