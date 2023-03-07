package pro.sky.telegram_bot_pets_shelter.service;

import pro.sky.telegram_bot_pets_shelter.entity.Owner;

import java.util.List;
import java.util.Optional;

public interface OwnerService {
    boolean createOwner(Owner owner);
    Optional<Owner> findOwnerById(long id);
    Optional<Owner> findOwnerByChatId(long id);
    Owner editOwner(Owner owner);
    Owner DeleteOwner(Long id);
    List<Owner> getAllOwners();
    boolean checkAdoptionCat(Owner owner);
    boolean checkAdoptionDog(Owner owner);
}
