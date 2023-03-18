package pro.sky.telegram_bot_pets_shelter.exception_handling;

public class BlackListNotFoundException extends RuntimeException {
    public BlackListNotFoundException() {
    }

    public BlackListNotFoundException(String message) {
        super(message);
    }

    public BlackListNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BlackListNotFoundException(Throwable cause) {
        super(cause);
    }

    public BlackListNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
