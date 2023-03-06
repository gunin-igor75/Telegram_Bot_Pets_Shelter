package pro.sky.telegram_bot_pets_shelter.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegram_bot_pets_shelter.entity.Cat;
import pro.sky.telegram_bot_pets_shelter.service.CatService;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/cat")
public class CatController {

    final private CatService service;

    public CatController(CatService service) {
        this.service = service;
    }


    @GetMapping("{id}")
    public ResponseEntity<Cat> findPet(@PathVariable Long id) {
        Optional<Cat> pet = service.findCat(id);
        if (pet.isPresent()) {
            return ResponseEntity.ok(pet.get());
        }
        throw new RuntimeException();
    }

    @PutMapping
    public ResponseEntity<Cat> editCat(@RequestBody Cat cat) {
        return ResponseEntity.ok(service.editCat(cat));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Cat> deleteCat(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteCat(id));
    }

    @GetMapping
    public ResponseEntity<List<Cat>> getAllCats() {
        return ResponseEntity.ok(service.getAllCatsFree());
    }

}
