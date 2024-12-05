package design_pattern.facade;

import design_pattern.factory.AccountFactory;
import design_pattern.factory.TransactionFactory;
import design_pattern.singleton.Bank;
import model.Account;
import model.Transaction;

public class BankFacade {
    private Bank bank = Bank.getInstance();

    public void createAccount(String accountType) {
        Account account = AccountFactory.createAccount(accountType);
        bank.addAccount(account);
    }

    public void transferFunds(Account from, Account to, double amount) {
        Transaction transaction = TransactionFactory.createTransaction(from, to, amount);
        bank.addTransaction(transaction);
    }
}
