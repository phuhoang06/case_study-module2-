package design_pattern.factory;

import model.Account;
import model.Transaction;

public class TransactionFactory {
    public static Transaction createTransaction(Account from, Account to, double amount) {
        return new Transaction(from, to, amount);
    }
}
