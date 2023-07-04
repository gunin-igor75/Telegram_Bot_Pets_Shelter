package pro.sky.telegram_bot_pets_shelter.exception_handling;

public class DogNotFoundException extends RuntimeException {

    public DogNotFoundException(String message) {
        super(message);
    }

}
