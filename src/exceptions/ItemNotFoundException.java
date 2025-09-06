package exceptions;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(String code) {
        super("Товар с кодом " + code + " не найден.");
    }
}
