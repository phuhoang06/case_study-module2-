package controller;

import model.Account;

public class BankServiceFacade {
    private BankController bankController;

    public BankServiceFacade(BankController bankController) {
        this.bankController = bankController;
    }

    public void depositMoney(Account account, double amount) {
        bankController.depositMoney(account, amount);
    }

    public void withdrawMoney(Account account, double amount) {
        bankController.withdrawMoney(account, amount);
    }

    public void transferMoney(Account fromAccount, Account toAccount, double amount) {
        bankController.transferMoney(fromAccount, toAccount, amount);
    }

    public double checkBalance(Account account) {
        return bankController.checkBalance(account);
    }
}
