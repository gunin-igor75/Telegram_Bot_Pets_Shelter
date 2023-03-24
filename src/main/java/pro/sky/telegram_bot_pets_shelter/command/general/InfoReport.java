package pro.sky.telegram_bot_pets_shelter.command.general;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.telegram_bot_pets_shelter.command.Command;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

@Component
@Slf4j
public class InfoReport implements Command {

    private final MessageUtils messageUtils;

    public InfoReport(MessageUtils messageUtils) {
        this.messageUtils = messageUtils;
    }

    @Override
    public SendMessage execute(Update update) {
        var text = "После нажатия кнопки 'Отправить отчет' отправьте отчет \n" +
                ": сначала фотография, а в ее описании текстовая часть \n" +
                "содержащая информацию о питомце с обязательными  ключевыми" +
                " словами - Диета  - рацион питания \n" +
                "- Здоровье - как здоровье\n" +
                "- Поведение - поведение\n" +
                "Если отправить отчет раздумали, то нажмите кнопку 'Закрыть'";
        return messageUtils.generationSendMessage(update,text);
    }
}
