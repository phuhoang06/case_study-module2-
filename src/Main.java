import controller.BankController;
import controller.BankServiceFacade;
import model.*;
import storage.CsvFileStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws AccountNotFoundException {
        CsvFileStorage storage = CsvFileStorage.getInstance();

        // Đọc dữ liệu từ file CSV
        List<Account> accounts = storage.readAccountData("accounts.csv");

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
                loggedInAccount = bankController.findAccountByEmailAndPassword(email, password);
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

            // Kiểm tra xem email đã tồn tại chưa
            while (isEmailExist(accounts, customerEmail)) {
                System.out.println("Email đã tồn tại, vui lòng nhập email khác.");
                System.out.print("Nhập email khách hàng: ");
                customerEmail = scanner.nextLine();
            }

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

            // Ghi lại dữ liệu vào file CSV
            storage.writeAccountData(accounts, "accounts.csv");

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
                    Account recipientAccount = null;
                    try {
                        recipientAccount = bankController.findAccountByEmail(recipientEmail);
                    } catch (AccountNotFoundException e) {
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

    // Kiểm tra xem email đã tồn tại trong danh sách tài khoản chưa
    private static boolean isEmailExist(List<Account> accounts, String email) {
        for (Account account : accounts) {
            if (account.getCustomer().getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }
}

