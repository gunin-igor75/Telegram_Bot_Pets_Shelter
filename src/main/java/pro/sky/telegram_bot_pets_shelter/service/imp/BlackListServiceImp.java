package pro.sky.telegram_bot_pets_shelter.service.imp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.telegram_bot_pets_shelter.entity.BlackList;
import pro.sky.telegram_bot_pets_shelter.exception_handling.BlackListNotFoundException;
import pro.sky.telegram_bot_pets_shelter.repositories.BlackListRepository;
import pro.sky.telegram_bot_pets_shelter.service.BlackListService;

import java.util.List;

@Service
@Slf4j
public class BlackListServiceImp implements BlackListService {
    private final BlackListRepository blackListRepository;

    public BlackListServiceImp(BlackListRepository blackListRepository) {
        this.blackListRepository = blackListRepository;
    }
    @Override
    public BlackList createBlackList(BlackList blackList) {
        checkBlackListNull(blackList);
        if (blackList.getId() == null) {
            return blackListRepository.save(blackList);
        }
        return blackList;
    }
    @Override
    public BlackList findBlackList(Long id) {
        return blackListRepository.findById(id).orElse(null);
    }

    @Override
    public BlackList editBlackList(BlackList blackList) {
        checkBlackListNull(blackList);
        BlackList persistentBlackList = findBlackList(blackList.getId());
        if (persistentBlackList == null) {
            throw new BlackListNotFoundException();
        }
        return blackListRepository.save(persistentBlackList);
    }

    @Override
    public BlackList deleteBlackList(Long id) {
        BlackList blackList = findBlackList(id);
        if (blackList == null) {
            throw new BlackListNotFoundException();
        }
        blackListRepository.delete(blackList);
        return blackList;
    }

    @Override
    public List<BlackList> getAllBlackLists() {
        return blackListRepository.findAll();
    }

    private void checkBlackListNull(BlackList blackList) {
        if (blackList == null) {
            log.error("blackList is null");
            throw new NullPointerException();
        }
    }
}
