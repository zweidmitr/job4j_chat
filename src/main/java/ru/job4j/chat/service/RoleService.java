package ru.job4j.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.chat.domain.Role;
import ru.job4j.chat.repository.RoleRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepo;

    public List<Role> findAll() {
        return roleRepo.findAll();
    }

    public Role save(Role role) {
        return roleRepo.save(role);
    }

    public Optional<Role> findById(int id) {
        return roleRepo.findById(id);
    }

    public void delete(int id) {
        roleRepo.delete(findById(id).get());
    }

    public Optional<Role> findByName(String name) {
        return roleRepo.findOneByName(name);
    }

}
