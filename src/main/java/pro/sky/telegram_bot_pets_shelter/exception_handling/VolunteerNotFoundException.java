package pro.sky.telegram_bot_pets_shelter.exception_handling;

public class VolunteerNotFoundException extends RuntimeException {
    public VolunteerNotFoundException() {
    }

    public VolunteerNotFoundException(String message) {
        super(message);
    }

    public VolunteerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public VolunteerNotFoundException(Throwable cause) {
        super(cause);
    }

    public VolunteerNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
