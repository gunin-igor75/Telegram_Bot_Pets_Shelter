package pro.sky.telegram_bot_pets_shelter.service;

import org.telegram.telegrambots.meta.api.objects.User;
import pro.sky.telegram_bot_pets_shelter.entity.Owner;

import java.time.LocalDate;
import java.util.List;

public interface OwnerService {
    Owner createOwner(Owner owner);
    Owner findOwner(Long id);
    Owner findOwnerByChatId(Long id);
    Owner editOwner(Owner owner);
    Owner deleteOwner(Long id);
    List<Owner> getAllOwners();

    Owner findOrSaveOwner(User telegramUser);

    String registration(Long chatId);

    boolean checkNoAdoptionCat(Owner owner);
    boolean checkNoAdoptionDog(Owner owner);

    List<Owner> getOwnerCatsEndTestPeriod(LocalDate date);

    List<Owner> getOwnerDogsEndTestPeriod(LocalDate date);
}
