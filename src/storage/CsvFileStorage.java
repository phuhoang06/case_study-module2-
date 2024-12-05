package storage;

import model.Account;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvFileStorage {
    private static CsvFileStorage instance;
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String FILE_HEADER = "accountId,name,balance,email,password";

    private CsvFileStorage() {
        // Private constructor to prevent instantiation
    }

    public static CsvFileStorage getInstance() {
        if (instance == null) {
            synchronized (CsvFileStorage.class) {
                if (instance == null) {
                    instance = new CsvFileStorage();
                }
            }
        }
        return instance;
    }

    public void writeAccountData(List<Account> accounts, String fileName) {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.append(FILE_HEADER);
            fileWriter.append(NEW_LINE_SEPARATOR);
            for (Account account : accounts) {
                fileWriter.append(String.valueOf(account.getAccountId()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(account.getAccountName());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(account.getBalance()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(account.getCustomer().getEmail());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(account.getCustomer().getPassword());
                fileWriter.append(NEW_LINE_SEPARATOR);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
