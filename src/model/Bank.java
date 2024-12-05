package model;

import java.util.List;

public class Bank {
    private static Bank instance;
    private List<Customer> customers;
    private List<Account> accounts;
    private List<Loan> loans;

    private Bank() {
        // Private constructor to prevent instantiation.
    }
    public static Bank getInstance() {
        if (instance == null) {
            instance = new Bank();
        }
        return instance;
    }

    // Methods to manage customers, accounts, transactions, loans, etc.
}
