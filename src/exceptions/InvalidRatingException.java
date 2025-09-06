package exceptions;

public class InvalidRatingException extends Exception {
    public InvalidRatingException(int rateValue) {
        super("Рейтинг (" + rateValue + ") может принимать значение от 1 до 5");
    }
}
