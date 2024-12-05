package model;

public class AccountFactory {
    public static Account createAccount(int id, String accountName, double balance, Customer customer) {
        return new Account(id, accountName, balance, customer);
    }
}
