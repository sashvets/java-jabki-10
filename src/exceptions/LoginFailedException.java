package exceptions;

public class LoginFailedException extends RuntimeException {
    public LoginFailedException() {
        super("Ошибка аутентификации: неверный логин или пароль.");
    }
}
