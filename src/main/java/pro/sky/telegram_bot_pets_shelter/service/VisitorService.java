package pro.sky.telegram_bot_pets_shelter.service;

import pro.sky.telegram_bot_pets_shelter.entity.Visitor;

import java.util.List;
import java.util.Optional;

public interface VisitorService {
    boolean creteVisitor(Visitor visitor);
    Optional<Visitor> findVisitor(Long id);

    Optional<Visitor> findVisitorByChatId(long id);

    Visitor editVisitor(Visitor visitor);
    Visitor deleteVisitor(Long id);
    List<Visitor> getAllVisitors();
}
