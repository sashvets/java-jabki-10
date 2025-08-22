package coffee;

public class CoffeeMaker {
    private CoffeeMaker() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static void makeCoffee(boolean hasWater) {
        try {
            if (hasWater) {
                System.out.println("Кофе готов!");
            } else {
                throw new NoWaterException();
            }
        } catch (NoWaterException e) {
            System.out.println("Ошибка! " + e.getMessage());
        } finally {
            System.out.println("Очистка кофемашины");
        }

    }
}