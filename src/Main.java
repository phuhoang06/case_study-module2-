import controller.BankController;
import controller.BankServiceFacade;
import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws AccountNotFoundException {
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

        // Chọn giữa đăng nhập hoặc đăng ký
        System.out.println("Chào mừng đến với hệ thống ngân hàng");
        System.out.println("1. Đăng nhập");
        System.out.println("2. Đăng ký tài khoản mới");
        System.out.print("Chọn một tùy chọn (1 hoặc 2): ");
        int choice = scanner.nextInt();

        Account loggedInAccount = null;

        if (choice == 1) {
            // Đăng nhập
            System.out.print("Nhập email: ");
            String email = scanner.next();
            System.out.print("Nhập mật khẩu: ");
            String password = scanner.next();

            try {
                loggedInAccount = bankController.findAccountByEmail(email, password);
                System.out.println("Đăng nhập thành công! Chào mừng " + loggedInAccount.getCustomer().getName());
            } catch (AccountNotFoundException e) {
                System.out.println("Lỗi: " + e.getMessage());
                return;
            }

        } else if (choice == 2) {
            // Đăng ký tài khoản mới
            System.out.println("Đăng ký tài khoản mới:");

            scanner.nextLine();  // Consume newline
            System.out.print("Nhập tên khách hàng: ");
            String customerName = scanner.nextLine();
            System.out.print("Nhập email khách hàng: ");
            String customerEmail = scanner.nextLine();
            System.out.print("Nhập mật khẩu khách hàng: ");
            String customerPassword = scanner.nextLine();

            System.out.print("Nhập tên tài khoản: ");
            String accountName = scanner.nextLine();
            System.out.print("Nhập số dư ban đầu: ");
            double initialBalance = scanner.nextDouble();

            // Tạo khách hàng mới
            Customer newCustomer = CustomerFactory.createCustomer(accounts.size() + 1, customerName, customerEmail, customerPassword);

            // Tạo tài khoản mới
            Account newAccount = AccountFactory.createAccount(accounts.size() + 1, accountName, initialBalance, newCustomer);

            // Thêm tài khoản mới vào danh sách
            accounts.add(newAccount);
            System.out.println("Tài khoản đã được tạo thành công!");

            loggedInAccount = newAccount;
            System.out.println("Đăng nhập thành công! Chào mừng " + loggedInAccount.getCustomer().getName());
        } else {
            System.out.println("Tùy chọn không hợp lệ.");
            return;
        }

        // Menu chính sau khi đăng nhập thành công
        while (true) {
            System.out.println("\nChọn một tùy chọn:");
            System.out.println("1. Gửi tiền");
            System.out.println("2. Rút tiền");
            System.out.println("3. Chuyển tiền");
            System.out.println("4. Kiểm tra số dư");
            System.out.println("5. Thoát");

            int menuChoice = scanner.nextInt();

            switch (menuChoice) {
                case 1:
                    System.out.print("Nhập số tiền gửi: ");
                    double depositAmount = scanner.nextDouble();
                    bankFacade.depositMoney(loggedInAccount, depositAmount);
                    break;
                case 2:
                    System.out.print("Nhập số tiền rút: ");
                    double withdrawAmount = scanner.nextDouble();
                    bankFacade.withdrawMoney(loggedInAccount, withdrawAmount);
                    break;
                case 3:
                    System.out.print("Nhập email người nhận: ");
                    scanner.nextLine();  // Consume newline
                    String recipientEmail = scanner.nextLine();
                    Account recipientAccount = bankController.findAccountByEmail(recipientEmail, loggedInAccount.getCustomer().getPassword());
                    if (recipientAccount == null) {
                        System.out.println("Không tìm thấy người nhận.");
                        break;
                    }
                    System.out.print("Nhập số tiền chuyển: ");
                    double transferAmount = scanner.nextDouble();
                    bankFacade.transferMoney(loggedInAccount, recipientAccount, transferAmount);
                    break;
                case 4:
                    double balance = bankFacade.checkBalance(loggedInAccount);
                    System.out.println("Số dư tài khoản của bạn là: " + balance);
                    break;
                case 5:
                    System.out.println("Cảm ơn bạn đã sử dụng dịch vụ ngân hàng của chúng tôi!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Tùy chọn không hợp lệ.");
            }
        }
    }
}
