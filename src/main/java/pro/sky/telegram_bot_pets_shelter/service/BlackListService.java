package pro.sky.telegram_bot_pets_shelter.service;

import pro.sky.telegram_bot_pets_shelter.entity.BlackList;

import java.util.List;

public interface BlackListService {
    BlackList createBlackList(BlackList blackList);

    BlackList findBlackList(Long id);

    BlackList editBlackList(BlackList blackList);

    BlackList deleteBlackList(Long id);

    List<BlackList> getAllBlackLists();
}
