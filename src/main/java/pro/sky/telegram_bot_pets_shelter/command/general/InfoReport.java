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
        var text = "После на жатия кнопки 'Send report' отправьте отчет \n" +
                ":сначала фотография, а в ее описании текстовая часть \n" +
                "содержащая информацию о питомце с обязательными  ключевыми" +
                " словами - diet  - рацион питания \n" +
                "- health - как здоровье\n" +
                "- behavior - поведение\n" +
                "Если отправить отчет раздумали, то нажмите кнопку 'cancel";
        return messageUtils.generationSendMessage(update,text);
    }
}
