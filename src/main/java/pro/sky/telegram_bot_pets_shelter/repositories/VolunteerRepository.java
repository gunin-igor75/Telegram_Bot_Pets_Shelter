package pro.sky.telegram_bot_pets_shelter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegram_bot_pets_shelter.entity.Volunteer;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
}
