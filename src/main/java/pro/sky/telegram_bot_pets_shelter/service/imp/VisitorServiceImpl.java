package pro.sky.telegram_bot_pets_shelter.service.imp;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import pro.sky.telegram_bot_pets_shelter.entity.Visitor;
import pro.sky.telegram_bot_pets_shelter.exception_handling.VisitorNotFoundException;
import pro.sky.telegram_bot_pets_shelter.repositories.VisitorRepository;
import pro.sky.telegram_bot_pets_shelter.service.VisitorService;

import java.util.List;

import static pro.sky.telegram_bot_pets_shelter.service.enums.UserState.BASIC_STATE;

@Service
public class VisitorServiceImpl implements VisitorService {
    private final VisitorRepository visitorRepository;

    public VisitorServiceImpl(VisitorRepository visitorRepository) {
        this.visitorRepository = visitorRepository;
    }

    @Override
    public Visitor creteVisitor(Visitor visitor) {
        Visitor persistentVisitor = findVisitor(visitor.getId());
        if (persistentVisitor == null) {
            persistentVisitor = visitorRepository.save(visitor);
        }
        return persistentVisitor;
    }

    @Override
    public Visitor findVisitor(Long id) {
        return visitorRepository.findById(id).orElse(null);
    }

    @Override
    public Visitor findVisitorByChatId(long id) {
        return visitorRepository.findVisitorByChatId(id).orElse(null);
    }

    @Override
    public Visitor editVisitor(Visitor visitor) {
        Visitor persistentVisitor = findVisitor(visitor.getId());
        if (persistentVisitor == null) {
            throw new VisitorNotFoundException();
        }
        return visitorRepository.save(visitor);
    }

    @Override
    public Visitor findOrSaveVisitor(User telegramUser) {
        Visitor persistentVisitor = findVisitorByChatId(telegramUser.getId());
        if (persistentVisitor == null) {
            Visitor transientVisitor = Visitor.builder()
                    .chatId(telegramUser.getId())
                    .firstname(telegramUser.getFirstName())
                    .lastname(telegramUser.getLastName())
                    .username(telegramUser.getUserName())
                    .state(BASIC_STATE)
                    .lastAction("start")
                    .build();
            persistentVisitor = creteVisitor(transientVisitor);
        }
        return persistentVisitor;
    }

    @Override
    public Visitor deleteVisitor(Long id) {
        Visitor visitor = findVisitor(id);
        if (visitor == null) {
            throw new VisitorNotFoundException();
        }
        visitorRepository.delete(visitor);
        return visitor;
    }

    @Override
    public List<Visitor> getAllVisitors() {
        return visitorRepository.findAll();
    }
}
