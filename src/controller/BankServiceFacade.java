package controller;

import model.Account;
import model.AccountNotFoundException;
import model.InsufficientFundsException;
import model.InvalidAmountException;

public class BankServiceFacade {
    private BankController bankController;

    public BankServiceFacade(BankController bankController) {
        this.bankController = bankController;
    }

    public void depositMoney(Account account, double amount) {
        try {
            bankController.deposit(account, amount);
            System.out.println("Gửi tiền thành công!");
        } catch (InvalidAmountException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    public void withdrawMoney(Account account, double amount) {
        try {
            bankController.withdraw(account, amount);
            System.out.println("Rút tiền thành công!");
        } catch (InsufficientFundsException | InvalidAmountException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    public void transferMoney(Account fromAccount, Account toAccount, double amount) {
        try {
            bankController.transfer(fromAccount, toAccount, amount);
            System.out.println("Chuyển tiền thành công!");
        } catch (InsufficientFundsException | InvalidAmountException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    public double checkBalance(Account account) {
        return bankController.checkBalance(account);
    }
}
