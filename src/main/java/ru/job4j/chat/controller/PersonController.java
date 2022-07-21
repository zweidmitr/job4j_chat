package ru.job4j.chat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.domain.Person;
import ru.job4j.chat.domain.Role;
import ru.job4j.chat.domain.Room;
import ru.job4j.chat.service.PersonService;

import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;
    private final BCryptPasswordEncoder encoder;

    @GetMapping("/persons")
    public List<Person> findAll() {
        return personService.findAll();
    }

    @GetMapping("/persons/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        return personService.findById(id)
                .map(person -> new ResponseEntity<>(person, HttpStatus.OK))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Person with id: %d is not found", id)));
    }

    @PostMapping("/person")
    public ResponseEntity<Person> create(@RequestBody Person person) {

        return new ResponseEntity<>(personService.save(person), HttpStatus.CREATED);
    }

    @GetMapping("/persons/{room}")
    public List<Person> findAllPeopleInRoom(@PathVariable("room") String room) {
        return personService.findAllPeopleInRoom(room);
    }

    @PatchMapping("/personsp")
    public ResponseEntity<Person> path(@RequestBody Person person) {
        var current = personService.findById(person.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Person with id: %s is  not found", person.getId())));
        if (person.getLogin() != null) {
            current.setLogin(person.getLogin());
        }
        if (person.getPassword() != null && person.getPassword().length() > 2) {
            current.setPassword(encoder.encode(person.getPassword()));
        }
        if (person.getRole() != null) {
            current.setRole(person.getRole());
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(personService.save(current));
    }
}
