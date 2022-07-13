package ru.job4j.chat.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.chat.domain.Role;
import ru.job4j.chat.domain.Room;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    List<Role> findAll();

    Optional<Role> findOneByName(String name);
}
