package pro.sky.telegram_bot_pets_shelter.exception_handling;

public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException() {
    }

    public TaskNotFoundException(String message) {
        super(message);
    }

    public TaskNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskNotFoundException(Throwable cause) {
        super(cause);
    }

    public TaskNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
