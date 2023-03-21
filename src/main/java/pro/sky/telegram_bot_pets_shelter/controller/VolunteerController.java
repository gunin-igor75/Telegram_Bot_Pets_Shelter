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
import pro.sky.telegram_bot_pets_shelter.entity.Volunteer;
import pro.sky.telegram_bot_pets_shelter.exception_handling.ShelterIncorrectData;
import pro.sky.telegram_bot_pets_shelter.exception_handling.VolunteerNotFoundException;
import pro.sky.telegram_bot_pets_shelter.service.OwnerService;
import pro.sky.telegram_bot_pets_shelter.service.VolunteerService;

import java.util.List;

@RestController
@RequestMapping("/volunteer")
@Tag(name = "VolunteerController", description = "Взаимодействие с волонтерами приюта")
@Slf4j
public class VolunteerController {
    private final VolunteerService volunteerService;
    private final OwnerService ownerService;

    public VolunteerController(VolunteerService volunteerService, OwnerService ownerService) {
        this.volunteerService = volunteerService;
        this.ownerService = ownerService;
    }

    @Operation(
            summary = "Поиск волонтера по id",
            description = "Позволяет найти волонтера по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Поиск волонтера по id",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Volunteer.class)
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
    public ResponseEntity<Volunteer> findVolunteer(@PathVariable Long id) {
        Volunteer persistentVolunteer = volunteerService.findVolunteer(id);
        if (persistentVolunteer == null) {
            throw new VolunteerNotFoundException();
        }
        return ResponseEntity.ok(persistentVolunteer);
    }

    @Operation(
            summary = "Сохранение нового волонтера в базу данных",
            description = "Позволяет сохранить волонтера в базу данных",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Сохранение волонтера",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Volunteer.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Сохранение нового волонтера в базу данных",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Volunteer.class)
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
    public ResponseEntity<Volunteer> createVolunteer(@RequestBody Volunteer volunteer) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(volunteerService.createVolunteer(volunteer));
    }

    @Operation(
            summary = "Изменение скилов волонтера в базе данных",
            description = "Позволяет изменить параметры волонтера",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Редактируемый волонтер",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Volunteer.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Изменение скилов волонтера в базе данных",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Volunteer.class)
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
    public ResponseEntity<Volunteer> editVolunteer(@RequestBody Volunteer volunteer) {
        return ResponseEntity.ok(volunteerService.editVolunteer(volunteer));
    }

    @Operation(
            summary = "Удаление волонтера по id",
            description = "Позволяет удалить волонтера по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаление волонтера по id",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Volunteer.class)
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
    public ResponseEntity<Volunteer> deleteVolunteer(@PathVariable Long id) {
        return ResponseEntity.ok(volunteerService.deleteVolunteer(id));
    }

    @Operation(
            summary = "Наличие всех волонтеров в приюте",
            description = "Позволяет показать всех волонтеров приюта",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "Наличие всех волонтеров в приюте",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(
                                    schema = @Schema(implementation = Volunteer.class)
                            )
                    )
            )
    )
    @GetMapping
    public ResponseEntity<List<Volunteer>> getAllVolunteers() {
        return ResponseEntity.ok(volunteerService.getAllVolunteers());
    }


    @Operation(
            summary = "Регистрация пользователей",
            description = "Позволяет зарегистрировать пользователя",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Идентификатор пользователя(chat id)",
                    content = @Content(
                            mediaType = MediaType.ALL_VALUE,
                            schema = @Schema(implementation = Long.class)
                    )
            ),
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "Регистрация пользователя",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            )
    )
    @PostMapping("/registration")
    public ResponseEntity<String> registrationOwner(@RequestBody Long chatId) {
        return ResponseEntity.ok(ownerService.registration(chatId));
    }
}
