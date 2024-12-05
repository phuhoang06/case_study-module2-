import controller.BankController;
import controller.BankServiceFacade;
import model.Account;
import model.Customer;
import model.AccountFactory;
import model.CustomerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Account> accounts = new ArrayList<>();

        // Tạo khách hàng và tài khoản bằng Factory Pattern
        Customer customer1 = CustomerFactory.createCustomer(1, "John Doe", "john.doe@example.com", "password123");
        Account account1 = AccountFactory.createAccount(1, "John's Savings", 1000.0, customer1);
        accounts.add(account1);

        Customer customer2 = CustomerFactory.createCustomer(2, "Jane Doe", "jane.doe@example.com", "password456");
        Account account2 = AccountFactory.createAccount(2, "Jane's Savings", 500.0, customer2);
        accounts.add(account2);

        // Khởi tạo BankController
        BankController bankController = new BankController(accounts);

        // Khởi tạo BankServiceFacade
        BankServiceFacade bankFacade = new BankServiceFacade(bankController);

        Scanner scanner = new Scanner(System.in);

        // Đăng nhập
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        Account loggedInAccount = bankController.findAccountByEmail(email, password);
        if (loggedInAccount == null) {
            System.out.println("Invalid email or password.");
            return;
        }

        System.out.println("Logged in successfully! Welcome " + loggedInAccount.getCustomer().getName());

        // Menu
        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Transfer");
            System.out.println("4. Check Balance");
            System.out.println("5. Create a new Account");
            System.out.println("6. Exit");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = scanner.nextDouble();
                    bankFacade.depositMoney(loggedInAccount, depositAmount);
                    System.out.println("Deposited " + depositAmount + " successfully.");
                    break;
                case 2:
                    System.out.print("Enter withdraw amount: ");
                    double withdrawAmount = scanner.nextDouble();
                    bankFacade.withdrawMoney(loggedInAccount, withdrawAmount);
                    System.out.println("Withdrawn " + withdrawAmount + " successfully.");
                    break;
                case 3:
                    System.out.print("Enter recipient email: ");
                    scanner.nextLine();  // Consume newline
                    String recipientEmail = scanner.nextLine();
                    Account recipientAccount = bankController.findAccountByEmail(recipientEmail, password);
                    if (recipientAccount == null) {
                        System.out.println("Recipient not found.");
                        break;
                    }
                    System.out.print("Enter transfer amount: ");
                    double transferAmount = scanner.nextDouble();
                    bankFacade.transferMoney(loggedInAccount, recipientAccount, transferAmount);
                    System.out.println("Transferred " + transferAmount + " successfully.");
                    break;
                case 4:
                    double balance = bankFacade.checkBalance(loggedInAccount);
                    System.out.println("Your balance is: " + balance);
                    break;
                case 5:
                    // Thêm chức năng tạo tài khoản mới
                    System.out.println("Create a new account:");

                    scanner.nextLine();  // Consume newline
                    System.out.print("Enter customer name: ");
                    String customerName = scanner.nextLine();
                    System.out.print("Enter customer email: ");
                    String customerEmail = scanner.nextLine();
                    System.out.print("Enter customer password: ");
                    String customerPassword = scanner.nextLine();

                    System.out.print("Enter account name: ");
                    String accountName = scanner.nextLine();
                    System.out.print("Enter initial balance: ");
                    double initialBalance = scanner.nextDouble();

                    // Tạo khách hàng mới
                    Customer newCustomer = CustomerFactory.createCustomer(accounts.size() + 1, customerName, customerEmail, customerPassword);

                    // Tạo tài khoản mới
                    Account newAccount = AccountFactory.createAccount(accounts.size() + 1, accountName, initialBalance, newCustomer);

                    // Thêm tài khoản mới vào danh sách
                    accounts.add(newAccount);
                    System.out.println("Account created successfully!");

                    // Ghi lại dữ liệu tài khoản vào file CSV
                    bankFacade.depositMoney(newAccount, 0);  // Ghi lại tài khoản vào file

                    break;
                case 6:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }
}
