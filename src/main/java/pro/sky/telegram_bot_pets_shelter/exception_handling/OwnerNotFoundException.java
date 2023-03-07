package pro.sky.telegram_bot_pets_shelter.exception_handling;

public class OwnerNotFoundException extends RuntimeException {
    public OwnerNotFoundException() {
    }

    public OwnerNotFoundException(String message) {
        super(message);
    }

    public OwnerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public OwnerNotFoundException(Throwable cause) {
        super(cause);
    }

    public OwnerNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
