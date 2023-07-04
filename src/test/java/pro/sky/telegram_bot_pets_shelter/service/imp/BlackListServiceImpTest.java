package pro.sky.telegram_bot_pets_shelter.service.imp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegram_bot_pets_shelter.exception_handling.BlackListNotFoundException;
import pro.sky.telegram_bot_pets_shelter.repositories.BlackListRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static pro.sky.telegram_bot_pets_shelter.Value.*;

@ExtendWith(MockitoExtension.class)
class BlackListServiceImpTest {
    @Mock
    private BlackListRepository blackListRepository;
    @InjectMocks
    private BlackListServiceImp blackListServiceImp;
    @Test
    void createBlackListTest() {
        when(blackListRepository.save(blackListFirst)).thenReturn(blackListSecond);
        assertThat(blackListServiceImp.createBlackList(blackListFirst)).isEqualTo(blackListSecond);
        assertThat(blackListServiceImp.createBlackList(blackListSecond)).isEqualTo(blackListSecond);
        assertThatThrownBy(() -> blackListServiceImp.createBlackList(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void findBlackListTes() {
        when(blackListRepository.findById(200L)).thenReturn(Optional.of(blackListSecond));
        assertThat(blackListServiceImp.findBlackList(200L)).isEqualTo(blackListSecond);
        assertThat(blackListServiceImp.findBlackList(1L)).isNotEqualTo(blackListFirst);
        assertThat(blackListServiceImp.findBlackList(null)).isEqualTo(null);
    }

    @Test
    void editBlackListTest() {
        when(blackListRepository.findById(200L)).thenReturn(Optional.ofNullable(blackListSecond));
        when(blackListRepository.save(blackListSecond)).thenReturn(blackListSecond);
        assertThat(blackListServiceImp.editBlackList(blackListSecond)).isEqualTo(blackListSecond);
        assertThatThrownBy(() -> blackListServiceImp.editBlackList(blackListFirst))
                .isInstanceOf(BlackListNotFoundException.class);
        assertThatThrownBy(() -> blackListServiceImp.editBlackList(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void deleteBlackListTest() {
        when(blackListRepository.findById(200L)).thenReturn(Optional.ofNullable(blackListSecond));
        blackListServiceImp.deleteBlackList(200L);
        verify(blackListRepository, atLeastOnce()).delete(blackListSecond);
        assertThatThrownBy(() -> blackListServiceImp.deleteBlackList(1L))
                .isInstanceOf(BlackListNotFoundException.class);
    }

    @Test
    void getAllDogsTest() {
        when(blackListRepository.findAll()).thenReturn(List.of(blackListFirst, blackListSecond));
        assertThat(blackListServiceImp.getAllBlackLists()).isEqualTo(List.of(blackListFirst, blackListSecond));
    }
}