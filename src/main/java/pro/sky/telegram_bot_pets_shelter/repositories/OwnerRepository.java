package pro.sky.telegram_bot_pets_shelter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegram_bot_pets_shelter.entity.Owner;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
}