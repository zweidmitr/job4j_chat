package ru.job4j.chat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.job4j.chat.domain.Person;
import ru.job4j.chat.service.PersonService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/chat/persons")
@RequiredArgsConstructor
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class.getSimpleName());
    private final ObjectMapper objectMapper;
    private final PersonService users;
    private final BCryptPasswordEncoder encoder;

    @PostMapping("/sign-up")
    public void singUp(@RequestBody Person person) {
        String login = person.getLogin();
        String password = person.getPassword();
        if (login == null || password == null) {
            throw new NullPointerException("Username and password mustn't be empty");
        }
        if (password.length() < 3) {
            throw new IllegalArgumentException("Invalid  password length");
        }

        person.setPassword(encoder.encode(person.getPassword()));
        users.save(person);
    }

    @ExceptionHandler(value = {NullPointerException.class, IllegalArgumentException.class})
    public void handleException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() {{
            put("message", e.getMessage());
            put("details", "go to deBug");
        }}));
        LOGGER.error(e.getLocalizedMessage());
    }
}
