package pro.sky.telegram_bot_pets_shelter.command.general;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.telegram_bot_pets_shelter.command.Command;
import pro.sky.telegram_bot_pets_shelter.entity.Visitor;
import pro.sky.telegram_bot_pets_shelter.service.VisitorService;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;


/**
 * Данный класс формрует сообщения исодя из выбора start:
 * Сохраняет в ьазу данных visitor
 */
@Component
public class Start implements Command {
    private final MessageUtils messageUtils;
    private final VisitorService visitorService;

    public Start(MessageUtils messageUtils, VisitorService visitorService) {
        this.messageUtils = messageUtils;
        this.visitorService = visitorService;
    }

    @Override
    public SendMessage execute(Update update) {
        Visitor visitor = visitorService.findOrSaveVisitor(update.getMessage().getFrom());
        return messageUtils.generationSendMessage(update, "Здравствуйте " + visitor.getUsername() +
                    " Вас приветствует бот приюта!");
    }
}
