package model;

public class Account {
    private int id;
    private String accountName;
    private double balance;
    private Customer customer;

    public Account(int id, String accountName, double balance, Customer customer) {
        this.id = id;
        this.accountName = accountName;
        this.balance = balance;
        this.customer = customer;
    }

    public int getId() {
        return id;
    }

    public String getAccountName() {
        return accountName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Customer getCustomer() {
        return customer;
    }
}
