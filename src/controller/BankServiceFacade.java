package controller;

import model.Account;
import model.InsufficientFundsException;
import model.InvalidAmountException;
import storage.CsvFileStorage;

import java.util.List;

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

    public void transferMoney(Account sender, Account recipient, double amount) {
        try {
            // Kiểm tra số dư của người gửi
            if (sender.getBalance() < amount) {
                throw new InsufficientFundsException("Không đủ số dư để thực hiện chuyển tiền.");
            }

            // Kiểm tra số tiền hợp lệ
            if (amount <= 0) {
                throw new InvalidAmountException("Số tiền chuyển phải lớn hơn 0.");
            }

            // Trừ tiền từ tài khoản người gửi
            sender.setBalance(sender.getBalance() - amount);

            // Cộng tiền vào tài khoản người nhận
            recipient.setBalance(recipient.getBalance() + amount);

            System.out.println("Chuyển tiền thành công! Đã chuyển " + amount + " từ tài khoản "
                    + sender.getAccountName() + " đến tài khoản " + recipient.getAccountName());

            // Lưu lại thay đổi vào file CSV sau mỗi giao dịch
            CsvFileStorage storage = CsvFileStorage.getInstance();
            List<Account> updatedAccounts = storage.readAccountData("accounts.csv");
            storage.writeAccountData(updatedAccounts, "accounts.csv");

        } catch (InsufficientFundsException | InvalidAmountException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    public double checkBalance(Account account) {
        return bankController.checkBalance(account);
    }
}
