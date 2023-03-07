package pro.sky.telegram_bot_pets_shelter.exception_handling;

public class DogNotFoundException extends RuntimeException {
    public DogNotFoundException() {
    }

    public DogNotFoundException(String message) {
        super(message);
    }

    public DogNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DogNotFoundException(Throwable cause) {
        super(cause);
    }

    public DogNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
