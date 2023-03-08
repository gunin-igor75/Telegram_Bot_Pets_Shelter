package pro.sky.telegram_bot_pets_shelter.service;

import org.telegram.telegrambots.meta.api.objects.User;
import pro.sky.telegram_bot_pets_shelter.entity.Owner;

import java.util.List;

public interface OwnerService {
    Owner createOwner(Owner owner);
    Owner findOwner(long id);
    Owner findOwnerByChatId(long id);
    Owner editOwner(Owner owner);
    Owner DeleteOwner(Long id);
    List<Owner> getAllOwners();

    Owner findOrSaveOwner(User telegramUser);

    boolean checkAdoptionCat(Owner owner);
    boolean checkAdoptionDog(Owner owner);
}
