package ru.job4j.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.chat.domain.Person;
import ru.job4j.chat.domain.Role;
import ru.job4j.chat.repository.PersonRepository;
import ru.job4j.chat.repository.RoleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepo;
    private final RoleRepository roleRepo;

    public List<Person> findAll() {
        return personRepo.findAll();
    }

    public Person save(Person person) {
        if (person.getRole() == null) {
            Role role = roleRepo.findById(1).get();
            person.setRole(role);
        }
        return personRepo.save(person);
    }

    public List<Person> findAllPeopleInRoom(String room) {
        return personRepo.findAllPeopleInRoom(room);
    }

    public Person findByLogin(String name) {
        return personRepo.findByLogin(name);
    }
}
