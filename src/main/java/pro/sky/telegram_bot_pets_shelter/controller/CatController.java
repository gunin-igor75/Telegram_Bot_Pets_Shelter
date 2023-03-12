package pro.sky.telegram_bot_pets_shelter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegram_bot_pets_shelter.entity.Cat;
import pro.sky.telegram_bot_pets_shelter.entity.Dog;
import pro.sky.telegram_bot_pets_shelter.exception_handling.CatNotFoundException;
import pro.sky.telegram_bot_pets_shelter.service.CatService;

import java.util.List;


@RestController
@RequestMapping("/cat")
public class CatController {

    final private CatService catService;

    public CatController(CatService catService) {
        this.catService = catService;
    }


    @GetMapping("{id}")
    public ResponseEntity<Cat> findCat(@PathVariable Long id) {
        Cat persistentCat = catService.findCat(id);
        if (persistentCat == null) {
            throw new CatNotFoundException();
        }
        return ResponseEntity.ok(persistentCat);
    }

    @PostMapping
    public ResponseEntity<Cat> createCat(@RequestBody Cat cat) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(catService.createCat(cat));
    }

    @PutMapping
    public ResponseEntity<Cat> editCat(@RequestBody Cat cat) {
        return ResponseEntity.ok(catService.editCat(cat));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Cat> deleteCat(@PathVariable Long id) {
        return ResponseEntity.ok(catService.deleteCat(id));
    }

    @GetMapping
    public ResponseEntity<List<Cat>> getAllCats() {
        return ResponseEntity.ok(catService.getAllCatsFree());
    }
}
