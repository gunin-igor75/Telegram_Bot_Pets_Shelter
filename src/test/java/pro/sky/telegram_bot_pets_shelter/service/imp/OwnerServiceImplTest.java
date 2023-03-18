package pro.sky.telegram_bot_pets_shelter.service.imp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.User;
import pro.sky.telegram_bot_pets_shelter.entity.Owner;
import pro.sky.telegram_bot_pets_shelter.exception_handling.CatNotFoundException;
import pro.sky.telegram_bot_pets_shelter.exception_handling.DogNotFoundException;
import pro.sky.telegram_bot_pets_shelter.exception_handling.OwnerNotFoundException;
import pro.sky.telegram_bot_pets_shelter.exception_handling.ReportNotFoundException;
import pro.sky.telegram_bot_pets_shelter.repositories.OwnerRepository;
import pro.sky.telegram_bot_pets_shelter.service.enums.UserState;


import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static pro.sky.telegram_bot_pets_shelter.Value.*;
import static pro.sky.telegram_bot_pets_shelter.service.enums.UserState.BASIC_STATE;
import static pro.sky.telegram_bot_pets_shelter.service.enums.UserState.REPORT_CATS_STATE;

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
        assertThat(ownerService.createOwner(ownerBS)).isEqualTo(ownerBS);
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
    void findOrSaveOwnerTest() {
        when(ownerRepository.findById(123L)).thenReturn(Optional.of(ownerBS));
        assertThat(ownerService.findOwner(123L)).isEqualTo(ownerBSStart);
        assertThat(ownerService.findOwner(124L)).isNotEqualTo(ownerBS);
        assertThat(ownerService.findOwner(null)).isEqualTo(null);
    }

}