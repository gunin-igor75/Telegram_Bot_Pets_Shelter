package pro.sky.telegram_bot_pets_shelter.service;

import pro.sky.telegram_bot_pets_shelter.entity.Volunteer;

import java.util.List;

public interface VolunteerService {
    Volunteer createVolunteer(Volunteer volunteer);

    Volunteer findVolunteer(Long id);

    Volunteer editVolunteer(Volunteer volunteer);

    Volunteer deleteVolunteer(Long id);

    List<Volunteer> getAllVolunteers();
}
