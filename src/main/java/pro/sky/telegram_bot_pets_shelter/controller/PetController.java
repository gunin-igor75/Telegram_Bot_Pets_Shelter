package pro.sky.telegram_bot_pets_shelter.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegram_bot_pets_shelter.entity.Pet;
import pro.sky.telegram_bot_pets_shelter.service.PetService;

import java.util.List;


@RestController
@RequestMapping("/pet")
public class PetController {

    final private PetService service;

    public PetController(PetService service) {
        this.service = service;
    }


    @GetMapping("{id}")
    public ResponseEntity<Pet> findPet(@PathVariable Long id) {
        return ResponseEntity.ok(service.findPet(id));
    }

    @PutMapping
    public ResponseEntity<Pet> editPet(@RequestBody Pet pet) {
        return ResponseEntity.ok(service.editPet(pet));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Pet> deletePet(@PathVariable Long id) {
        return ResponseEntity.ok(service.deletePet(id));
    }

    @GetMapping
    public ResponseEntity<List<Pet>> getAllPets() {
        return ResponseEntity.ok(service.getAllPetsFree());
    }

}
