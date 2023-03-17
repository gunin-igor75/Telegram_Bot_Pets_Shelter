package pro.sky.telegram_bot_pets_shelter.service.imp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.telegram_bot_pets_shelter.entity.Volunteer;
import pro.sky.telegram_bot_pets_shelter.exception_handling.VolunteerNotFoundException;
import pro.sky.telegram_bot_pets_shelter.repositories.VolunteerRepository;
import pro.sky.telegram_bot_pets_shelter.service.VolunteerService;

import java.util.List;

/**
 * Данный класс выполняет следующие действия:
 * -ежедневно проверяет базу данных на наличие
 * окончания испытательного срока - считает report для конкретного cat
 * - отправляет через бота - окончание испытательного срока
 * - отправляет через бота - продление испытательного срока
 * - раз в 2 дня проверяет на наличие bad report и помечает как проверенные report
 * - отправляет через бота пользователю, что он "редиска"
 */
@Service
@Slf4j
public class VolunteerServiceImp implements VolunteerService {
    private final VolunteerRepository volunteerRepository;

    public VolunteerServiceImp(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    @Override
    public Volunteer createVolunteer(Volunteer volunteer) {
        checkVolunteerNull(volunteer);
        if (volunteer.getId() == null) {
            return volunteerRepository.save(volunteer);
        }
        return volunteer;
    }

    @Override
    public Volunteer findVolunteer(Long id) {
        return volunteerRepository.findById(id).orElse(null);
    }

    @Override
    public Volunteer editVolunteer(Volunteer volunteer) {
        checkVolunteerNull(volunteer);
        Volunteer persistentVolunteer = findVolunteer(volunteer.getId());
        if (persistentVolunteer == null) {
            throw new VolunteerNotFoundException();
        }
        return volunteerRepository.save(persistentVolunteer);
    }

    @Override
    public Volunteer deleteVolunteer(Long id) {
        Volunteer volunteer = findVolunteer(id);
        if (volunteer == null) {
            throw new VolunteerNotFoundException();
        }
        volunteerRepository.delete(volunteer);
        return volunteer;
    }

    @Override
    public List<Volunteer> getAllVolunteers() {
        return volunteerRepository.findAll();
    }

    private void checkVolunteerNull(Volunteer volunteer) {
        if (volunteer == null) {
            log.error("volunteer is null");
            throw new NullPointerException();
        }
    }
}
