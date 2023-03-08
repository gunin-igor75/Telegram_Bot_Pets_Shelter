package pro.sky.telegram_bot_pets_shelter.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegram_bot_pets_shelter.entity.Dog;
import pro.sky.telegram_bot_pets_shelter.exception_handling.DogNotFoundException;
import pro.sky.telegram_bot_pets_shelter.service.DogService;

import java.util.List;


@RestController
@RequestMapping("/dog")
public class DogController {
    private final DogService dogService;
    public DogController(DogService dogService) {
        this.dogService = dogService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Dog> findDog(@PathVariable Long id) {
        Dog persistentDog = dogService.findDog(id);
        if (persistentDog == null) {
            throw new DogNotFoundException();
        }
        return ResponseEntity.ok(persistentDog);
    }

    @PutMapping
    public ResponseEntity<Dog> editDog(@RequestBody Dog dog) {
        return ResponseEntity.ok(dogService.editDog(dog));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Dog> deleteDog(@PathVariable Long id) {
        return ResponseEntity.ok(dogService.deleteDog(id));
    }

    @GetMapping
    public ResponseEntity<List<Dog>> getAllDogs() {
        return ResponseEntity.ok(dogService.getAllDogsFree());
    }
}
