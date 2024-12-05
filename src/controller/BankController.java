package controller;

import model.Account;
import model.AccountNotFoundException;
import model.InsufficientFundsException;
import model.InvalidAmountException;

import java.util.List;

public class BankController {
    private List<Account> accounts;

    public BankController(List<Account> accounts) {
        this.accounts = accounts;
    }

    public Account findAccountByEmail(String email) throws AccountNotFoundException {
        for (Account account : accounts) {
            if (account.getCustomer().getEmail().equals(email)) {
                return account;
            }
        }
        throw new AccountNotFoundException("Tài khoản không tồn tại.");
    }

    public boolean validateAccount(String email, String password) {
        for (Account account : accounts) {
            if (account.getCustomer().getEmail().equals(email) && account.getCustomer().getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }



    public void withdraw(Account account, double amount) throws InsufficientFundsException, InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Số tiền rút phải lớn hơn 0.");
        }
        if (account.getBalance() < amount) {
            throw new InsufficientFundsException("Số dư không đủ để thực hiện giao dịch.");
        }
        account.setBalance(account.getBalance() - amount);
    }

    public void deposit(Account account, double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Số tiền gửi phải lớn hơn 0.");
        }
        account.setBalance(account.getBalance() + amount);
    }

    public void transfer(Account fromAccount, Account toAccount, double amount) throws InsufficientFundsException, InvalidAmountException {
        withdraw(fromAccount, amount);
        deposit(toAccount, amount);
    }

    public double checkBalance(Account account) {
        return account.getBalance();
    }
}
