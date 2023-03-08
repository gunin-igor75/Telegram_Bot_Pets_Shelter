package pro.sky.telegram_bot_pets_shelter.service;

import org.telegram.telegrambots.meta.api.objects.User;
import pro.sky.telegram_bot_pets_shelter.entity.Visitor;

import java.util.List;
import java.util.Optional;

public interface VisitorService {
    Visitor creteVisitor(Visitor visitor);
    Visitor findVisitor(Long id);

    Visitor findVisitorByChatId(long id);

    Visitor editVisitor(Visitor visitor);

    Visitor findOrSaveVisitor(User telegramUser);

    Visitor deleteVisitor(Long id);
    List<Visitor> getAllVisitors();
}
