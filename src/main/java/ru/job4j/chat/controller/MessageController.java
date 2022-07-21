package ru.job4j.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.domain.Message;
import ru.job4j.chat.domain.Person;
import ru.job4j.chat.domain.Room;
import ru.job4j.chat.service.MessageService;
import ru.job4j.chat.service.RoomService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private final RoomService roomService;
    private static final String API = "http://localhost:8080/chat/persons/";
    private static final String API_ROOM = "http://localhost:8080/chat/room/{name}";
    @Autowired
    private RestTemplate rest;

    @GetMapping("/room/{room}/people")
    public List<Person> findAllPeople(@PathVariable("room") String name) {
        ResponseEntity<List<Person>> response = rest.exchange(API + name,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Person>>() {
                });
        System.out.println("test");
        return response.getBody();
    }

    @PostMapping("/room/{nameroom}/{username}/message")
    public ResponseEntity<Message> create(
            @PathVariable("nameroom") String nameroom,
            @PathVariable("username") String username,
            @RequestBody Message message) {
        Optional<Room> roomByName = roomService.findByName(nameroom);
        if (roomByName.isEmpty()) {
            return ResponseEntity.ok().build();
        }
        message.setRoom(roomByName.get());
        return new ResponseEntity<>(
                messageService.save(message, username),
                HttpStatus.CREATED);
    }

    @GetMapping("/room/{room}/message")
    public List<Message> findAll(@PathVariable("room") String roomName) {
        Room room = rest.getForEntity(API_ROOM, Room.class, roomName).getBody();
        return messageService.findByRoom(room);
    }

    @PatchMapping("/messagep")
    public ResponseEntity<Message> path(@RequestBody Message message) {
        var current = messageService.findById(message.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Message with id: %s is  not found", message.getId())));
        if (message.getText() != null) {
            current.setText(message.getText());
        }
        if (message.getPerson() != null) {
            current.setPerson(message.getPerson());
        }
        if (message.getRoom() != null) {
            current.setRoom(message.getRoom());
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(messageService.save(current, current.getRoom().getName()));
    }
}
