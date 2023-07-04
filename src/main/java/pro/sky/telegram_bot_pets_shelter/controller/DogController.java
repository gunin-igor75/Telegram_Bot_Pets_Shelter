package pro.sky.telegram_bot_pets_shelter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegram_bot_pets_shelter.entity.Dog;
import pro.sky.telegram_bot_pets_shelter.exception_handling.DogNotFoundException;
import pro.sky.telegram_bot_pets_shelter.exception_handling.ShelterIncorrectData;
import pro.sky.telegram_bot_pets_shelter.service.DogService;

import java.util.List;


@RestController
@RequestMapping("/dog")
@Tag(name = "DogController", description = "Взаимодействие с песиками приюта")
@Slf4j
public class DogController {
    private final DogService dogService;

    public DogController(DogService dogService) {
        this.dogService = dogService;
    }

    @Operation(
            summary = "Поиск песика по id",
            description = "Позволяет найти пеиска по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Поиск песика по id",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Dog.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ShelterIncorrectData.class)
                            )
                    )
            }
    )
    @GetMapping("{id}")
    public ResponseEntity<Dog> findDog(@PathVariable Long id) {
        Dog persistentDog = dogService.findDog(id);
        if (persistentDog == null) {
            String message = "There is no dog with ID =" + id + " in Database";
            log.error(message);
            throw new DogNotFoundException(message);
        }
        return ResponseEntity.ok(persistentDog);
    }

    @Operation(
            summary = "Сохранение нового песика в базу данных",
            description = "Позволяет сохранить песика в базу данных",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Сохранение песика",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Dog.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Сохранение нового песика в базу данных",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Dog.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ShelterIncorrectData.class)
                            )

                    )
            }
    )
    @PostMapping
    public ResponseEntity<Dog> createDog(@RequestBody Dog dog) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(dogService.createDog(dog));
    }

    @Operation(
            summary = "Изменение скилов песика в базе данных",
            description = "Позволяет изменить параметры песика",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Редактируемый песик",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Dog.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Изменение скилов песика в базе данных",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Dog.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ShelterIncorrectData.class)
                            )

                    )
            }
    )
    @PutMapping
    public ResponseEntity<Dog> editDog(@RequestBody Dog dog) {
        return ResponseEntity.ok(dogService.editDog(dog));
    }

    @Operation(
            summary = "Удаление песика по id",
            description = "Позволяет удалить пеиска по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаление песика по id",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Dog.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ShelterIncorrectData.class)
                            )
                    )
            }
    )
    @DeleteMapping("{id}")
    public ResponseEntity<Dog> deleteDog(@PathVariable Long id) {
        return ResponseEntity.ok(dogService.deleteDog(id));
    }

    @Operation(
            summary = "Наличие всех песиков в приюте",
            description = "Позволяет показать всех песиков приюта",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "Наличие всех песиков в приюте",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(
                                    schema = @Schema(implementation = Dog.class)
                            )
                    )
            )
    )
    @GetMapping
    public ResponseEntity<List<Dog>> getAllDogs() {
        return ResponseEntity.ok(dogService.getAllDogs());
    }
}
