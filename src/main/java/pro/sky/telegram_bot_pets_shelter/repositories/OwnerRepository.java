package pro.sky.telegram_bot_pets_shelter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.telegram_bot_pets_shelter.entity.Owner;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Owner findByChatId(Long id);

    @Query(value = "select c.name from owner o " +
            "inner join cat c on o.cat_id=c.id " +
            "where o.id=?1 and c.adopted=false ",
            nativeQuery = true)
    String getCatNoAdoption(long id);

    @Query(value = "select d.name from owner o " +
            "inner join dog d on o.dog_id=d.id " +
            "where o.id=?1 and d.adopted=false ",
            nativeQuery = true)
    String getDogNoAdoption(long id);

    @Query(value = "select o.* from owner o " +
            "inner join cat c on c.id = o.cat_id " +
            "where c.date_adoption + c.test_period =?1",
            nativeQuery = true)
    List<Owner> getOwnerCatsEndTestPeriod(LocalDate date);

    @Query(value = "select o.* from owner o " +
            "inner join dog d on d.id = o.dog_id " +
            "where d.date_adoption + d.test_period =?1",
            nativeQuery = true)
    List<Owner> getOwnerDogsEndTestPeriod(LocalDate date);


}