package exceptions;

public class NegativeDepositException extends Exception {
    public NegativeDepositException(double amount) {
        super("Депозит не может быть пополнен отрицательной суммой: " + amount);
    }
}
