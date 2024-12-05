package controller;

import model.Account;
import storage.CsvFileStorage;

import java.util.List;

public class BankController {
    private List<Account> accounts;
    private CsvFileStorage csvFileStorage;

    public BankController(List<Account> accounts) {
        this.accounts = accounts;
        this.csvFileStorage = CsvFileStorage.getInstance();
    }

    public Account findAccountByEmail(String email, String password) {
        for (Account account : accounts) {
            if (account.getCustomer().getEmail().equals(email) && account.getCustomer().getPassword().equals(password)) {
                return account;
            }
        }
        return null;
    }

    public void depositMoney(Account account, double amount) {
        account.deposit(amount);
        csvFileStorage.writeAccountData(accounts, "accounts.csv");
    }

    public void withdrawMoney(Account account, double amount) {
        if (account.withdraw(amount)) {
            csvFileStorage.writeAccountData(accounts, "accounts.csv");
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    public void transferMoney(Account fromAccount, Account toAccount, double amount) {
        if (fromAccount.withdraw(amount)) {
            toAccount.deposit(amount);
            csvFileStorage.writeAccountData(accounts, "accounts.csv");
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    public double checkBalance(Account account) {
        return account.getBalance();
    }
}
