package pro.sky.telegram_bot_pets_shelter.exception_handling;

public class CatNotFoundException extends RuntimeException {
    public CatNotFoundException() {
    }

    public CatNotFoundException(String message) {
        super(message);
    }

    public CatNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CatNotFoundException(Throwable cause) {
        super(cause);
    }

    public CatNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
