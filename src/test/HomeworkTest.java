package test;

import exceptions.InsufficientBalanceException;
import exceptions.InvalidRatingException;
import exceptions.InvalidTransferAmountException;
import exceptions.ItemNotFoundException;
import exceptions.LoginFailedException;
import exceptions.NegativeDepositException;
import main.Homework;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Set;

class HomeworkTest {

    @Test
    void testSafeDivide() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        main.Homework.safeDivide(5, 0);
        Assertions.assertEquals("Деление на ноль запрещено", outputStream.toString().trim());
        Assertions.assertEquals(2.5, main.Homework.safeDivide(5, 2));
    }

    @Test
    void testCheckEmpty() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> main.Homework.checkEmpty(""));
        Assertions.assertThrows(IllegalArgumentException.class, () -> main.Homework.checkEmpty(null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> main.Homework.checkEmpty("   "));
        Assertions.assertDoesNotThrow(() -> Homework.checkEmpty("assertDoesNotThrow"));
    }

    @Test
    void testStringListToIntegerList() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        Assertions.assertEquals(Set.of(10, 5), Set.copyOf(main.Homework.stringListToIntegerList(List.of("10", "abc", "5"))));
        Assertions.assertEquals("Ошибка получения числа из строки: For input string: \"abc\"", outputStream.toString().trim());
    }

    @Test
    void testSetAge() {
        Exception exception;
        exception = Assertions.assertThrows(IllegalArgumentException.class, () -> main.Homework.setAge(-10));
        Assertions.assertEquals("Возраст не может быть меньше нуля", exception.getMessage());
        Assertions.assertDoesNotThrow(() -> main.Homework.setAge(10));
    }

    @Test
    void testDeposit() {
        Exception exception;
        exception = Assertions.assertThrows(NegativeDepositException.class, () -> main.Homework.deposit(-100));
        Assertions.assertEquals("Депозит не может быть пополнен отрицательной суммой: -100.0", exception.getMessage());
        Assertions.assertDoesNotThrow(() -> main.Homework.deposit(0));
        Assertions.assertDoesNotThrow(() -> main.Homework.deposit(100));
    }

    @Test
    void testGetItem() {
        Exception exception;
        exception = Assertions.assertThrows(ItemNotFoundException.class, () -> main.Homework.getItem("M0010"));
        Assertions.assertEquals("Товар с кодом M0010 не найден.", exception.getMessage());
        Assertions.assertEquals("Цемент", main.Homework.getItem("M001"));
    }

    @org.junit.jupiter.api.Test
    void testReadFile() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        main.Homework.readFile("src/_exceptions/NegativeDepositException.java");
        Assertions.assertEquals("src\\_exceptions\\NegativeDepositException.java (Системе не удается найти указанный путь)", outputStream.toString().trim());
        outputStream.reset();
        List<String> expectedLines = List.of(
                "package exceptions;",
                "",
                "public class NegativeDepositException extends Exception {",
                "    public NegativeDepositException(double amount) {",
                "        super(\"Депозит не может быть пополнен отрицательной суммой: \" + amount);",
                "    }",
                "}"
        );
        Assertions.assertEquals(expectedLines, main.Homework.readFile("src/exceptions/NegativeDepositException.java"));
    }

    @Test
    void testLogin() {
        Exception exception;
        exception = Assertions.assertThrows(LoginFailedException.class, () -> main.Homework.login("_JavaUser", "123456"));
        Assertions.assertEquals("Ошибка аутентификации: неверный логин или пароль.", exception.getMessage());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        Assertions.assertDoesNotThrow(() -> main.Homework.login("JavaUser", "123456"));
        Assertions.assertEquals("Успешная аутентификация.", outputStream.toString().trim());
        Assertions.assertThrows(LoginFailedException.class, () -> main.Homework.login("", "123456"));
        Assertions.assertThrows(LoginFailedException.class, () -> main.Homework.login("_JavaUser", ""));
        Assertions.assertThrows(LoginFailedException.class, () -> main.Homework.login("JavaUser", "1234567"));
        Assertions.assertThrows(LoginFailedException.class, () -> main.Homework.login(null, "1234567"));
        Assertions.assertThrows(LoginFailedException.class, () -> main.Homework.login("JavaUser", null));
    }

    @Test
    void testTransfer() {
        Exception exception;
        exception = Assertions.assertThrows(IllegalArgumentException.class, () -> main.Homework.transfer("_PascalUser", "PythonUser", 0.0));
        Assertions.assertEquals("Не найден счет отправителя: _PascalUser", exception.getMessage());
        exception = Assertions.assertThrows(IllegalArgumentException.class, () -> main.Homework.transfer("PascalUser", "_PythonUser", 0.0));
        Assertions.assertEquals("Не найден счет получателя: _PythonUser", exception.getMessage());
        exception = Assertions.assertThrows(IllegalArgumentException.class, () -> main.Homework.transfer("PascalUser", null, 0.0));
        Assertions.assertEquals("Для перевода необходимо ввести как отправителя так и получателя", exception.getMessage());
        exception = Assertions.assertThrows(IllegalArgumentException.class, () -> main.Homework.transfer(null, "PythonUser", 0.0));
        Assertions.assertEquals("Для перевода необходимо ввести как отправителя так и получателя", exception.getMessage());
        exception = Assertions.assertThrows(InvalidTransferAmountException.class, () -> main.Homework.transfer("PascalUser", "PythonUser", 0.0));
        Assertions.assertEquals("Сумма перевода (0.0) не может быть меньше или равна нулю.", exception.getMessage());
        exception = Assertions.assertThrows(InvalidTransferAmountException.class, () -> main.Homework.transfer("PascalUser", "PythonUser", -100.0));
        Assertions.assertEquals("Сумма перевода (-100.0) не может быть меньше или равна нулю.", exception.getMessage());
        exception = Assertions.assertThrows(InsufficientBalanceException.class, () -> main.Homework.transfer("PascalUser", "PythonUser", 100.0));
        Assertions.assertEquals("Недостаточно средств для перевода (100.0), баланс: 0.0", exception.getMessage());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        Assertions.assertDoesNotThrow(() -> main.Homework.transfer("JavaUser", "PythonUser", 500.0));
        Assertions.assertEquals("Перевод суммой 500.0 от JavaUser к PythonUser выполнен.", outputStream.toString().trim());
    }

    @Test
    void testRateProduct() {
        Exception exception;
        exception = Assertions.assertThrows(IllegalArgumentException.class, () -> main.Homework.rateProduct("Опилки", "10"));
        Assertions.assertEquals("Товар не найден: Опилки", exception.getMessage());
        exception = Assertions.assertThrows(InvalidRatingException.class, () -> main.Homework.rateProduct("Цемент", "10"));
        Assertions.assertEquals("Значение рейтинга должно быть в диапазоне от 1 до 5 (указано: 10)", exception.getMessage());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        Assertions.assertDoesNotThrow(() -> main.Homework.rateProduct("Цемент", "орпорп"));
        Assertions.assertEquals("Оценка товара должна быть числовым значением (введено: орпорп)", outputStream.toString().trim());
    }
}