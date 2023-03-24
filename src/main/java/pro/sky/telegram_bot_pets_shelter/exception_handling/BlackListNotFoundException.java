package pro.sky.telegram_bot_pets_shelter.exception_handling;

public class BlackListNotFoundException extends RuntimeException {
    public BlackListNotFoundException() {
    }

    public BlackListNotFoundException(String message) {
        super(message);
    }
}
