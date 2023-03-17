package pro.sky.telegram_bot_pets_shelter.exception_handling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pro.sky.telegram_bot_pets_shelter.entity.Volunteer;

@ControllerAdvice
@Slf4j
public class ShelterGlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ShelterIncorrectData> handlerException(DogNotFoundException exception) {
        var data = ShelterIncorrectData.of(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ShelterIncorrectData> handlerException(CatNotFoundException exception) {
        var data = ShelterIncorrectData.of(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ShelterIncorrectData> handlerException(VolunteerNotFoundException exception) {
        var data = ShelterIncorrectData.of(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ShelterIncorrectData> handlerException(Exception exception) {
        var data = ShelterIncorrectData.of(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }
}

