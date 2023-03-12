package pro.sky.telegram_bot_pets_shelter.exception_handling;

public class UploadFileEXception extends RuntimeException {
    public UploadFileEXception() {
    }

    public UploadFileEXception(String message) {
        super(message);
    }

    public UploadFileEXception(String message, Throwable cause) {
        super(message, cause);
    }

    public UploadFileEXception(Throwable cause) {
        super(cause);
    }

    public UploadFileEXception(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
