package pro.sky.telegram_bot_pets_shelter.service.imp;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import pro.sky.telegram_bot_pets_shelter.entity.BlackList;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class BlackListServiceImplRepositoryTest {
    @Autowired
    private BlackListServiceImp blackListService;
    @Test
    void findBlackListByChatIdTest() {
        BlackList igor = getBlackList("Igor", 100L);
        BlackList oleg = getBlackList("Oleg", 200L);
        BlackList sany = getBlackList("Sany", 300L);
        assertThat(blackListService.findBlackListByChatId(100L)).isEqualTo(igor);
        assertThat(blackListService.findBlackListByChatId(200L)).isEqualTo(oleg);
        assertThat(blackListService.findBlackListByChatId(300L)).isEqualTo(sany);
        assertThat(blackListService.findBlackList(400L)).isEqualTo(null);
    }

    private BlackList getBlackList(String username, long chatId) {
        BlackList blackList = BlackList.builder()
                .username(username)
                .chatId(chatId)
                .build();
        return blackListService.createBlackList(blackList);
    }
}