package model;

public class CustomerFactory {
    public static Customer createCustomer(int id, String name, String email, String password) {
        return new Customer(id, name, email, password);
    }
}
