package pro.sky.telegram_bot_pets_shelter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.telegram_bot_pets_shelter.entity.Cat;
import pro.sky.telegram_bot_pets_shelter.entity.Owner;

import java.util.List;
import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Optional<Owner> findByChatId(long id);

    @Query(value = "select c.name from owner o inner join cat c on o.cat_id=c.id where o.id=?1 and c.adopted=false ",
            nativeQuery = true)
    List<String> getCatAdoption(long id);

    @Query(value = "select d.name from owner o inner join dog d on o.dog_id=d.id where o.id=?1 and d.adopted=false ",
            nativeQuery = true)
    List<String> getDogAdoption(long id);
}