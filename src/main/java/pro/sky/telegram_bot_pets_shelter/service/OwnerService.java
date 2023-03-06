package pro.sky.telegram_bot_pets_shelter.service;

import pro.sky.telegram_bot_pets_shelter.entity.Owner;
import pro.sky.telegram_bot_pets_shelter.entity.Pet;

import java.util.List;

public interface OwnerService {
    Owner createOwner(Owner owner);
    Owner findOwner(Long id);
    Owner editOwner(Owner owner);
    Owner DeleteOwner(Long id);
    List<Owner> getAllOwners();

}
