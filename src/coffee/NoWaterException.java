package coffee;

public class NoWaterException extends Exception {

    public NoWaterException() {
        super("Нет воды!");
    }
}