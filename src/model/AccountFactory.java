package model;

public class AccountFactory {
    public static Account createAccount(int accountId, String accountName, double balance, Customer customer) {
        return new Account(accountId, accountName, balance, customer);
    }
}
