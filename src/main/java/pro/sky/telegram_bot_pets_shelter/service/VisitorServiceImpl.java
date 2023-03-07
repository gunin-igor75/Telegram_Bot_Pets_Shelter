package pro.sky.telegram_bot_pets_shelter.service;

import org.springframework.stereotype.Service;
import pro.sky.telegram_bot_pets_shelter.entity.Visitor;
import pro.sky.telegram_bot_pets_shelter.exception_handling.VisitorNotFoundException;
import pro.sky.telegram_bot_pets_shelter.repositories.VisitorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class VisitorServiceImpl implements VisitorService {

    private final VisitorRepository visitorRepository;

    public VisitorServiceImpl(VisitorRepository visitorRepository) {
        this.visitorRepository = visitorRepository;
    }

    @Override
    public boolean creteVisitor(Visitor visitor) {
        Optional<Visitor> newVisitor = findVisitor(visitor.getId());
        if (newVisitor.isPresent()) {
            return false;
        }
        visitorRepository.save(visitor);
        return true;
    }

    @Override
    public Optional<Visitor> findVisitor(Long id) {
        return visitorRepository.findById(id);
    }

    @Override
    public Optional<Visitor> findVisitorByChatId(long id) {
        return visitorRepository.findVisitorByChatId(id);
    }

    @Override
    public Visitor editVisitor(Visitor visitor) {
        Optional<Visitor> newVisitor = findVisitor(visitor.getId());
        if (newVisitor.isEmpty()) {
            throw new VisitorNotFoundException();
        }
        return visitorRepository.save(visitor);
    }

    @Override
    public Visitor deleteVisitor(Long id) {
        return null;
    }

    @Override
    public List<Visitor> getAllVisitors() {
        return null;
    }
}
