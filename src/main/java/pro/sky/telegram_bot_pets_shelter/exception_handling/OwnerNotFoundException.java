package pro.sky.telegram_bot_pets_shelter.exception_handling;

public class OwnerNotFoundException extends RuntimeException {
    public OwnerNotFoundException() {
    }

    public OwnerNotFoundException(String message) {
        super(message);
    }
}
