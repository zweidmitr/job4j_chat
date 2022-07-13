package ru.job4j.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.chat.domain.Room;
import ru.job4j.chat.repository.RoomRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepo;

    public List<Room> findAll() {
        return roomRepo.findAll();
    }

    public Room save(Room room) {
        return roomRepo.save(room);
    }

    public Optional<Room> findById(int id) {
        return roomRepo.findById(id);
    }

    public void delete(int id) {
        roomRepo.delete(findById(id).get());
    }

    public Optional<Room> findByName(String name) {
        return roomRepo.findOneByName(name);
    }
}
