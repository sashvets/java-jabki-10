package exceptions;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(double amount, double balance) {
        super("Недостаточно средств для перевода (" + amount + "), баланс: " + balance);
    }
}
