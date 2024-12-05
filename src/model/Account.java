package model;

public class Account {
    private int accountId;
    private String accountName;
    private double balance;
    private Customer customer;

    public Account(int accountId, String accountName, double balance, Customer customer) {
        this.accountId = accountId;
        this.accountName = accountName;
        this.balance = balance;
        this.customer = customer;
    }

    public int getAccountId() {
        return accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public double getBalance() {
        return balance;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public boolean withdraw(double amount) {
        if (this.balance >= amount) {
            this.balance -= amount;
            return true;
        }
        return false;
    }
}
