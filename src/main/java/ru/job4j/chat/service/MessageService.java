package ru.job4j.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.chat.domain.Message;
import ru.job4j.chat.domain.Person;
import ru.job4j.chat.domain.Room;
import ru.job4j.chat.repository.MessageRepository;
import ru.job4j.chat.repository.PersonRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepo;
    private final PersonRepository personRepo;

    public List<Message> findAll() {
        return messageRepo.findAll();
    }

    public Message save(Message message, String login) {
        Person personByLogin = personRepo.findPersonByLogin(login);
        message.setPerson(personByLogin);
        return messageRepo.save(message);
    }

    public List<Message> findByRoom(Room room) {
        return messageRepo.findByRoom(room);
    }
}
