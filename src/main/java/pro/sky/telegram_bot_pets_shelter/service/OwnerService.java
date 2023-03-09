package pro.sky.telegram_bot_pets_shelter.service;

import org.telegram.telegrambots.meta.api.objects.User;
import pro.sky.telegram_bot_pets_shelter.entity.Owner;
import pro.sky.telegram_bot_pets_shelter.service.enums.UserState;

import java.util.List;

public interface OwnerService {
    Owner createOwner(Owner owner);
    Owner findOwner(Long id);
    Owner findOwnerByChatId(long id);
    Owner editOwner(Owner owner);
    Owner DeleteOwner(Long id);
    List<Owner> getAllOwners();

    Owner findOrSaveOwner(User telegramUser);

    void editOwnerState(long id, UserState state);

    boolean checkAdoptionCat(Owner owner);
    boolean checkAdoptionDog(Owner owner);
}
