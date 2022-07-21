package ru.job4j.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.domain.Room;
import ru.job4j.chat.service.RoomService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @GetMapping("/rooms")
    public List<Room> findAll() {
        return roomService.findAll();
    }

    @PostMapping("/room")
    public ResponseEntity<Room> create(@RequestBody Room room) {
        String name = room.getName();
        if (name == null) {
            throw new NullPointerException("Name of room is empty");
        }
        return new ResponseEntity<>(roomService.save(room), HttpStatus.CREATED);
    }

    @DeleteMapping("room/{name}")
    public ResponseEntity<Void> delete(@PathVariable String name) {
        Optional<Room> roomByName = roomService.findByName(name);
        if (roomByName.isPresent()) {
            Room room = roomByName.get();
            roomService.delete(room.getId());
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("room/{name}")
    public ResponseEntity<Room> findByName(@PathVariable String name) {
        Optional<Room> room = roomService.findByName(name);

        return roomService.findByName(name)
                .map(res -> new ResponseEntity<>(res, HttpStatus.OK))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Room with name: %s is  not found", name)));
    }

    @PatchMapping("/roomp")
    public ResponseEntity<Room> path(@RequestBody Room room) {
        var current = roomService.findById(room.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Room with id: %s is  not found", room.getId())));
        if (room.getName() != null) {
            current.setName(room.getName());
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(roomService.save(current));
    }
}
