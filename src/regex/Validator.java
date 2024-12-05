package regex;

public class Validator {
    public static boolean validateAccountNumber(String accountNumber) {
        return accountNumber.matches("\\d{10}"); // Ví dụ số tài khoản có 10 chữ số
    }

    public static boolean validateEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    public static boolean validatePhoneNumber(String phone) {
        return phone.matches("^\\+?[0-9]{10,13}$");
    }
}

