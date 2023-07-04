package pro.sky.telegram_bot_pets_shelter.service.imp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.User;
import pro.sky.telegram_bot_pets_shelter.entity.Owner;
import pro.sky.telegram_bot_pets_shelter.exception_handling.OwnerNotFoundException;
import pro.sky.telegram_bot_pets_shelter.repositories.OwnerRepository;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static pro.sky.telegram_bot_pets_shelter.Value.*;
import static pro.sky.telegram_bot_pets_shelter.service.enums.UserState.BASIC_STATE;

@ExtendWith(MockitoExtension.class)
class OwnerServiceImplTest {
    @Mock
    private OwnerRepository ownerRepository;

    @InjectMocks
    private OwnerServiceImpl ownerService;


    @Test
    void createOwnerTest() {
        when(ownerRepository.save(owner)).thenReturn(ownerBS);
        assertThat(ownerService.createOwner(owner)).isEqualTo(ownerBS);
        assertThat(ownerService.createOwner(ownerId)).isEqualTo(ownerId);
        assertThatThrownBy(() -> ownerService.createOwner(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void findOwnerTest() {
        when(ownerRepository.findById(123L)).thenReturn(Optional.ofNullable(ownerBS));
        assertThat(ownerService.findOwner(123L)).isEqualTo(ownerBS);
        assertThat(ownerService.findOwner(124L)).isNotEqualTo(ownerBS);
        assertThat(ownerService.findOwner(null)).isEqualTo(null);
    }

    @Test
    void editOwnerTest() {
        when(ownerRepository.findById(11L)).thenReturn(Optional.of(ownerId));
        when(ownerRepository.save(ownerId)).thenReturn(ownerEdit);
        assertThat(ownerService.editOwner(ownerId)).isEqualTo(ownerEdit);
        assertThatThrownBy(() -> ownerService.editOwner(ownerNull))
                .isInstanceOf(OwnerNotFoundException.class);
        assertThatThrownBy(() -> ownerService.editOwner(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void deleteOwnerTest() {
        when(ownerRepository.findById(123L)).thenReturn(Optional.ofNullable(ownerBS));
        ownerService.deleteOwner(123L);
        verify(ownerRepository, atLeastOnce()).delete(ownerBS);
        assertThatThrownBy(() -> ownerService.deleteOwner(124L))
                .isInstanceOf(OwnerNotFoundException.class);
    }

    @Test
    void getAllOwnersTest() {
        when(ownerRepository.findAll()).thenReturn(List.of (ownerBS,ownerBSRegistrationTrue,ownerRCS, ownerRDS,ownerCats,ownerDogs));
        assertThat(ownerService.getAllOwners()).isEqualTo(List.of (ownerBS,ownerBSRegistrationTrue,ownerRCS, ownerRDS,ownerCats,ownerDogs));
    }

    @Test
    void findOrSaveOwnerIsNullTest() {
        User igor = getUser(100L, "Igor");
        Owner newOwner = getOwner(igor);
        newOwner.setId(1L);
        when(ownerRepository.findByChatId(any(Long.class))).thenReturn(null);
        when(ownerRepository.save(any(Owner.class))).thenReturn(newOwner);
        Owner actualOwner = ownerService.findOrSaveOwner(igor);
        assertThat(actualOwner).isEqualTo(newOwner);

    }

    @Test
    void findOrSaveOwnerIsNotNullTest() {
        User oleg = getUser(200L, "Oleg");
        Owner owner = getOwner(oleg);
        owner.setId(1L);
        when(ownerRepository.findByChatId(any(Long.class))).thenReturn(owner);
        Owner actualOwner = ownerService.findOrSaveOwner(oleg);
        assertThat(actualOwner).isEqualTo(owner);
    }

    @Test
    void registrationPositiveNoTest() {
        when(ownerRepository.findByChatId(any(Long.class))).thenReturn(ownerEdit);
        var textNotRegistration = "Sorry. You are already registered";
        String actualText = ownerService.registration(ownerEdit.getChatId());
        assertThat(actualText).isEqualTo(textNotRegistration);
    }

    @Test
    void registrationPositiveYesTest() {
        when(ownerRepository.findByChatId(any(Long.class))).thenReturn(owner);
        var textYesRegistration = "Congratulations. You have successfully registered";
        when( ownerRepository.findById(owner.getId())).thenReturn(Optional.of(owner));
        String actualText = ownerService.registration(owner.getChatId());
        assertThat(actualText).isEqualTo(textYesRegistration);
    }

    @Test
    void registrationNegativeTest() {
        when(ownerRepository.findByChatId(Mockito.any())).thenReturn(null);
        assertThatThrownBy(() -> ownerService.registration(ownerNull.getChatId()))
                .isInstanceOf(OwnerNotFoundException.class);
    }


    User getUser(long chatId, String username) {
        User user = new User();
        user.setId(chatId);
        user.setUserName(username);
        return user;
    }
    Owner getOwner(User user) {
        return Owner.builder()
                .registration(true)
                .username(user.getUserName())
                .chatId(user.getId())
                .state(BASIC_STATE)
                .dateRegistration(LocalDate.now())
                .build();
    }
}
