package exceptions;

public class InvalidRatingException extends Exception {
    public InvalidRatingException(int rateValue) {
        super("Значение рейтинга должно быть в диапазоне от 1 до 5 (указано: " + rateValue + ")");
    }
}
