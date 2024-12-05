package storage;

import model.Account;
import model.Customer;
import model.CustomerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
                fileWriter.append(String.valueOf(account.getId()));
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

    public List<Account> readAccountData(String fileName) {
        List<Account> accounts = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            // Bỏ qua header
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                if (values.length == 5) {
                    int accountId = Integer.parseInt(values[0]);
                    String accountName = values[1];
                    double balance = Double.parseDouble(values[2]);
                    String email = values[3];
                    String password = values[4];

                    Customer customer = CustomerFactory.createCustomer(accountId, "Unknown", email, password);
                    Account account = new Account(accountId, accountName, balance, customer);
                    accounts.add(account);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return accounts;
    }
}
