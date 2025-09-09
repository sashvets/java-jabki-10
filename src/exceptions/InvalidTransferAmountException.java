package exceptions;

public class InvalidTransferAmountException extends RuntimeException {
    public InvalidTransferAmountException(double amount) {
        super("Сумма перевода (" + amount + ") не может быть меньше или равна нулю.");
    }
}
