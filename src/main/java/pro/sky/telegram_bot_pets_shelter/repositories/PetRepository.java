package pro.sky.telegram_bot_pets_shelter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegram_bot_pets_shelter.entity.Pet;

public interface PetRepository extends JpaRepository<Pet, Long> {
}
