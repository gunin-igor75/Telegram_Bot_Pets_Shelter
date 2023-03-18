package pro.sky.telegram_bot_pets_shelter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegram_bot_pets_shelter.entity.BlackList;

public interface BlackListRepository extends JpaRepository<BlackList, Long> {

    BlackList getBlackListByChatId(long chatId);
}
