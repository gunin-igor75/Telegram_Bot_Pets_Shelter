package pro.sky.telegram_bot_pets_shelter.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegram_bot_pets_shelter.entity.Cat;
import pro.sky.telegram_bot_pets_shelter.exception_handling.CatNotFoundException;
import pro.sky.telegram_bot_pets_shelter.exception_handling.ShelterIncorrectData;
import pro.sky.telegram_bot_pets_shelter.service.CatService;
import java.util.List;


@RestController
@RequestMapping("/cat")
@Tag(name = "CatController", description = "Взаимодействие с котикам приюта")
@Slf4j
public class CatController {

final private CatService catService;
    public CatController(CatService catService) {
        this.catService = catService;
    }
    @Operation(
            summary = "Поиск кошки по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найдена кошка с параметрами",

                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Cat.class)
                            )

                    )
            },
            tags = "Работа с кошками"
    )
    @GetMapping("{id}")
    public ResponseEntity<Cat> findCat(@Parameter(description = "id кошки", example = "1") @PathVariable Long id) {
        Cat persistentCat = catService.findCat(id);
        if (persistentCat == null) {
            throw new CatNotFoundException();
        }
        return ResponseEntity.ok(persistentCat);
    }

    @Operation(
            summary = "Сохранение нового котика в базу данных",
            description = "Позволяет сохранить котика в базу данных",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Сохранение котика",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Cat.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Сохранение нового котика в базу данных",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Cat.class)
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
    public ResponseEntity<Cat> createCat(@RequestBody Cat cat) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(catService.createCat(cat));
    }

    @Operation(


            summary = "Редактирование кошки",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Обновленная кошка с параметрами",

                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Cat.class)
                            )

                    )
            },
            tags = "Работа с кошками",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Новые параметры кошки",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Cat.class)
                    )
            )

    )
    @PutMapping
    public ResponseEntity<Cat> editCat(@RequestBody Cat cat) {
        return ResponseEntity.ok(catService.editCat(cat));
    }


    @Operation(
            summary = "Удаление кошки по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удалена кошка с параметрами",

                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Cat.class)
                            )


                    )
            },
            tags = "Работа с кошками"

    )
    @DeleteMapping("{id}")
    public ResponseEntity<Cat> deleteCat(@PathVariable Long id) {
        return ResponseEntity.ok(catService.deleteCat(id));
    }


    @Operation(
            summary = "Поиск всех кошек",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список всех кошек",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Cat.class)
                            )
                    )
            },
            tags = "Работа с кошками"

    )
    @GetMapping
    public ResponseEntity<List<Cat>> getAllCats() {
        return ResponseEntity.ok(catService.getAllCats());
    }
}