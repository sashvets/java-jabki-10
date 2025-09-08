package main;

import exceptions.InsufficientBalanceException;
import exceptions.InvalidRatingException;
import exceptions.InvalidTransferAmountException;
import exceptions.ItemNotFoundException;
import exceptions.LoginFailedException;
import exceptions.NegativeDepositException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Homework {

    private static final Map<String, String> productList = new HashMap<>();

    static {
        productList.put("M001", "Цемент");
        productList.put("M002", "Кирпич");
        productList.put("M003", "Песок");
        productList.put("M004", "Щебень");
        productList.put("M005", "Арматура");
        productList.put("M006", "Доска обрезная");
    }

    private static final Map<String, String> users = new HashMap<>();

    static {
        users.put("JavaUser", "123456");
        users.put("PascalUser", "654321");
        users.put("PythonUser", "321123");
    }

    private static final Map<String, Double> accounts = new HashMap<>();

    static {
        accounts.put("JavaUser", 1000.00);
        accounts.put("PascalUser", 0.00);
        accounts.put("PythonUser", 15.00);
    }

    private static final Map<String, List<Integer>> ratingNote = new HashMap<>();

    static {
        ratingNote.put("Цемент", new ArrayList<>());
        ratingNote.put("Кирпич", new ArrayList<>());
        ratingNote.put("Гипсокартон", new ArrayList<>());
        ratingNote.put("Дюбель", new ArrayList<>());
        ratingNote.put("Профиль", new ArrayList<>());
    }

    public static void main(String[] args) {
        /**
         * 1. Безопасное деление.
         * Напишите метод safeDivide(int a, int b), который возвращает a / b.
         * Если b == 0, перехватите исключение и выведите сообщение: "Деление на ноль запрещено"
         */
        double resDiv;
        resDiv = safeDivide(5, 0);

        /**
         * 2. Проверка строки.
         * Напишите метод, который принимает строку и выбрасывает IllegalArgumentException,
         * если строка пуста или состоит только из пробелов.
         */
        String str;
        str = " ";
        try {
            checkEmpty(str);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        /**
         * 3. Преобразование строки в число.
         * Дан список строк List.of("10", "abc", "5").
         * Преобразуйте его в список чисел, перехватывая NumberFormatException.
         * Ошибки не должны останавливать выполнение.
         */
        List<String> stringList = new ArrayList<>(List.of("10", "abc", "5"));
        List<Integer> listNumbers = stringListToIntegerList(stringList);
        System.out.printf("Список строк %s преобразован в список чисел %s\n", stringList, listNumbers);

        /**
         * 4. Простая валидация возраста.
         * Метод setAge(int age) должен выбрасывать IllegalArgumentException, если возраст меньше нуля.
         * Обработайте исключение и выведите сообщение.
         */
        try {
            setAge(-10);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        /**
         * 5. Собственное исключение: депозит.
         * Создайте исключение NegativeDepositException, и метод deposit(double amount),
         * который выбрасывает это исключение при отрицательном значении.
         * Обработайте его в main.
         */
        try {
            deposit(-100);
        } catch (NegativeDepositException e) {
            System.out.println(e.getMessage());
        }
        /**
         * 6. Поиск товара по коду.
         * Реализуйте метод getItem(String code).
         * Если код не найден в карте товаров, выбросите ItemNotFoundException, унаследованное от RuntimeException.
         * Продемонстрируйте поведение в main.
         */
        String item1 = "M001";
        String item2 = "M0010";
        try {
            System.out.printf("Товар по коду %s : %s\n", item1, getItem(item1));
            System.out.printf("Товар по коду %s : %s\n", item1, getItem(item2));
        } catch (ItemNotFoundException e) {
            System.out.println(e.getMessage());
        }

        /**
         * 7. Чтение из файла.
         * Реализуйте метод readFile(String path), который читает текстовый файл и возвращает список строк.
         * Используйте BufferedReader, перехватите IOException, выведите сообщение об ошибке.
         */
        List<String> textFile = readFile("src/_exceptions/NegativeDepositException.java");
        textFile.forEach(System.out::println);

        /**
         * 8. Система логина.
         * Создайте метод login(String username, String password), в котором логин и пароль проверяются на корректность.
         * Если один из них не совпадает — выбрасывается LoginFailedException.
         * Исключение должно наследоваться от Exception.
         */
        try {
            login("_JavaUser", "123456");
        } catch (LoginFailedException e) {
            System.out.println(e.getMessage());
        }
        try {
            login("   ", "_123456");
        } catch (LoginFailedException e) {
            System.out.println(e.getMessage());
        }
        try {
            login("JavaUser", "123456");
        } catch (LoginFailedException e) {
            System.out.println(e.getMessage());
        }
        try {
            login("PascalUser", "654321");
        } catch (LoginFailedException e) {
            System.out.println(e.getMessage());
        }

        /**
         * 9. Банковский перевод с валидацией.
         * Метод transfer(fromAccount, toAccount, amount):
         *  - выбрасывает InvalidTransferAmountException, если сумма <= 0
         *  - выбрасывает InsufficientBalanceException, если баланс отправителя меньше суммы
         *  - содержит try-catch в main
         */
        transfer("JavaUser", "PascalUser", 500.00);
        transfer("PythonUser", "JavaUser", 10.00);
        transfer("PascalUser", "PythonUser", 250.00);
        try {
            transfer("JavaUser", "PascalUser", 2000.00);
        } catch (InsufficientBalanceException e) {
            System.out.println(e.getMessage());
        }
        try {
            transfer("PascalUser", "PythonUser", 0.00);
        } catch (InvalidTransferAmountException e) {
            System.out.println(e.getMessage());
        }
        try {
            transfer("PascalUser", "PythonUser", -10.00);
        } catch (InvalidTransferAmountException e) {
            System.out.println(e.getMessage());
        }
        try {
            transfer("", "PythonUser", 250.00);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        try {
            transfer("PascalUser", " ", 250.00);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        accounts.forEach((name, balance) ->
                System.out.println(name + " : " + balance + " руб."));

        /**
         * 10. Сервис оценки товара.
         * Реализуйте метод rateProduct(String rating), который:
         *  - принимает значение от 1 до 5
         *  - выбрасывает InvalidRatingException (checked), если значение вне диапазона
         *  - сохраняет рейтинг в списке, если всё хорошо
         *  - также перехватывает NumberFormatException, если рейтинг пришёл в виде строки, но содержит нечисловое значение
         */
        try {
            rateProduct("Кирпич", "5");
            rateProduct("Кирпич", "5");
            rateProduct("Кирпич", "4");
            rateProduct("Кирпич", "3");
            rateProduct("Цемент", "2");
            rateProduct("Цемент", "5");
            rateProduct("Цемент", "4");
            rateProduct("Цемент", "2");
            rateProduct("Гипсокартон", "3");
            rateProduct("Дюбель", "2");
            rateProduct("Дюбель", "2");
            rateProduct("Дюбель", "2");

        } catch (InvalidRatingException e) {
            System.out.println(e.getMessage());
        }
        List<String> products = new ArrayList<>(ratingNote.keySet());
        for (int i = 0; i < products.size(); i++) {
            String productName = products.get(i);
            List<Integer> productRates = ratingNote.get(productName);
            if (productRates.isEmpty()) {
                System.out.printf("%s : Нет оценок\n", productName);

            } else {
                double sum = 0;
                double average = 0;
                for (Integer productRate : productRates) {
                    sum += productRate;
                }
                average = sum / productRates.size();
                System.out.printf("%s : %.1f (%d)\n", productName, average, productRates.size());
            }
        }
    }

    /**
     * 1. Безопасное деление.
     * Напишите метод safeDivide(int a, int b), который возвращает a / b.
     * Если b == 0, перехватите исключение и выведите сообщение: "Деление на ноль запрещено"
     */
    public static double safeDivide(int a, int b) {
        try {
            int result = a / b;
            return (double) a / (double) b;
        } catch (ArithmeticException e) {
            System.out.println("Деление на ноль запрещено");
        }
        return 0;
    }

    /**
     * 2. Проверка строки.
     * Напишите метод, который принимает строку и выбрасывает IllegalArgumentException,
     * если строка пуста или состоит только из пробелов.
     */
    public static void checkEmpty(String str) {
        if (str == null || str.isBlank()) {
            throw new IllegalArgumentException("Строка содержит пробелы является пустой или is null");
        }
    }

    /**
     * 3. Преобразование строки в число.
     * Дан список строк List.of("10", "abc", "5").
     * Преобразуйте его в список чисел, перехватывая NumberFormatException.
     * Ошибки не должны останавливать выполнение.
     */
    public static List<Integer> stringListToIntegerList(List<String> list) {
        List<Integer> resList = new ArrayList<>();
        for (String s : list) {
            try {
                resList.add(Integer.valueOf(s));
            } catch (NumberFormatException e) {
                System.out.printf("Ошибка получения числа из строки: %s\n", e.getMessage());
            }
        }
        return resList;
    }

    /**
     * 4. Простая валидация возраста.
     * Метод setAge(int age) должен выбрасывать IllegalArgumentException, если возраст меньше нуля.
     * Обработайте исключение и выведите сообщение.
     */
    public static void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Возраст не может быть меньше нуля");
        }
    }

    /**
     * 5. Собственное исключение: депозит.
     * Создайте исключение NegativeDepositException, и метод deposit(double amount),
     * который выбрасывает это исключение при отрицательном значении.
     * Обработайте его в main.
     */
    public static void deposit(double amount) throws NegativeDepositException {
        if (amount < 0) {
            throw new NegativeDepositException(amount);
        }
    }

    /**
     * 6. Поиск товара по коду.
     * Реализуйте метод getItem(String code).
     * Если код не найден в карте товаров, выбросите ItemNotFoundException, унаследованное от RuntimeException.
     * Продемонстрируйте поведение в main.
     */
    public static String getItem(String code) throws ItemNotFoundException {
        String item = productList.get(code);
        if (item == null) {
            throw new ItemNotFoundException(code);
        }
        return item;
    }

    /**
     * 7. Чтение из файла.
     * Реализуйте метод readFile(String path), который читает текстовый файл и возвращает список строк.
     * Используйте BufferedReader, перехватите IOException, выведите сообщение об ошибке.
     */
    public static List<String> readFile(String path) {
        List<String> resLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                resLines.add(line);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return resLines;
    }

    /**
     * 8. Система логина.
     * Создайте метод login(String username, String password), в котором логин и пароль проверяются на корректность.
     * Если один из них не совпадает — выбрасывается LoginFailedException.
     * Исключение должно наследоваться от Exception.
     */
    public static void login(String username, String password) {
        if (username == null || password == null || username.isBlank() || password.isBlank()) {
            throw new LoginFailedException();
        }
        if (!Objects.equals(users.get(username), password)) {
            throw new LoginFailedException();
        }
        System.out.println("Успешная аутентификация.");
    }

    /**
     * 9. Банковский перевод с валидацией.
     * Метод transfer(fromAccount, toAccount, amount):
     * - выбрасывает InvalidTransferAmountException, если сумма <= 0
     * - выбрасывает InsufficientBalanceException, если баланс отправителя меньше суммы
     * - содержит try-catch в main
     */
    public static void transfer(String fromAccount, String toAccount, double amount) {
        if (fromAccount == null || toAccount == null || fromAccount.isBlank() || toAccount.isBlank()) {
            throw new IllegalArgumentException("Для перевода необходимо ввести как отправителя так и получателя");
        }
        if (!accounts.containsKey(fromAccount)) {
            throw new IllegalArgumentException("Не найден счет отправителя: " + fromAccount);
        }
        if (!accounts.containsKey(toAccount)) {
            throw new IllegalArgumentException("Не найден счет получателя: " + toAccount);
        }
        if (amount <= 0) {
            throw new InvalidTransferAmountException(amount);
        }
        double fromBalance = accounts.get(fromAccount);
        double toBalance = accounts.get(toAccount);
        if (fromBalance < amount) {
            throw new InsufficientBalanceException(amount, fromBalance);
        }
        accounts.put(fromAccount, fromBalance - amount);
        accounts.put(toAccount, toBalance + amount);
        System.out.println("Перевод суммой " + amount + " от " + fromAccount + " к " + toAccount + " выполнен.");
    }

    /**
     * 10. Сервис оценки товара.
     * Реализуйте метод rateProduct(String rating), который:
     * - принимает значение от 1 до 5
     * - выбрасывает InvalidRatingException (checked), если значение вне диапазона
     * - сохраняет рейтинг в списке, если всё хорошо
     * - также перехватывает NumberFormatException, если рейтинг пришёл в виде строки, но содержит нечисловое значение
     */
    public static void rateProduct(String product, String rating) throws InvalidRatingException {
        if (!ratingNote.containsKey(product)) {
            throw new IllegalArgumentException("Товар не найден: " + product);
        }
        int localRate;
        try {
            localRate = Integer.parseInt(rating);
            if (localRate < 1 || localRate > 5) {
                throw new InvalidRatingException(localRate);
            }
            ratingNote.get(product).add(localRate);
        } catch (NumberFormatException e) {
            System.out.printf("Оценка товара должна быть числовым значением (введено: %s)\n", rating);
        }
    }
}