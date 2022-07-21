package ru.job4j.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.domain.Role;
import ru.job4j.chat.domain.Room;
import ru.job4j.chat.service.RoleService;

import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/roles")
    public List<Role> findAll() {
        return roleService.findAll();
    }

    @GetMapping("/role/{id}")
    public ResponseEntity<Role> findById(@PathVariable int id) {
        return roleService.findById(id)
                .map(res -> new ResponseEntity<>(res, HttpStatus.OK))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Role with id %d is not found", id)));
    }

    @PostMapping("/role")
    public ResponseEntity<Role> create(@RequestBody Role role) {
        return new ResponseEntity<>(
                roleService.save(role), HttpStatus.CREATED
        );
    }

    @DeleteMapping("/role/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        roleService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/rolep")
    public ResponseEntity<Role> path(@RequestBody Role role) {
        var current = roleService.findById(role.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Role with id: %s is  not found", role.getId())));
        if (role.getName() != null) {
            current.setName(role.getName());
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(roleService.save(current));
    }
}
