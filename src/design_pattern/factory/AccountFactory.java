package design_pattern.factory;

import model.Account;

public class AccountFactory {
    public static Account createAccount(String accountType) {
        if (accountType.equalsIgnoreCase("Saving")) {
            return new SavingAccount();
        } else if (accountType.equalsIgnoreCase("Current")) {
            return new CurrentAccount();
        }
        return null;
    }
}

