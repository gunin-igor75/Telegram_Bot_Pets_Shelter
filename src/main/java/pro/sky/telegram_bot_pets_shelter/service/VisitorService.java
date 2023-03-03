package pro.sky.telegram_bot_pets_shelter.service;

import pro.sky.telegram_bot_pets_shelter.entity.Visitor;

import java.util.List;

public interface VisitorService {
    Visitor creteVisitor(Visitor visitor);
    Visitor findVisitor(Long id);
    Visitor editVisitor(Visitor visitor);
    Visitor deleteVisitor(Long id);
    List<Visitor> getAllVisitors();
}
