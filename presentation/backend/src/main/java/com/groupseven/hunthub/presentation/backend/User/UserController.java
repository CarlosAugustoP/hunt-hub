package com.groupseven.hunthub.presentation.backend.User;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.groupseven.hunthub.domain.models.User;
import com.groupseven.hunthub.domain.services.UserService;
import com.groupseven.hunthub.persistence.jpa.repository.UserRepositoryImpl;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepositoryImpl userRepository;

    @Autowired
    private UserService userService;

    public UserController(UserRepositoryImpl userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/login")
    public String login() {
        return "login";
    }


    /* Esse método vai dizer que não tem id no retorno do JSON, mas não se preocupar, no banco aparece tudo certo. */
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        userService.createUser(user);
        return user;
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable String id) {
        return userService.findUserById(id);
    }

    @DeleteMapping("/{id}")
    public boolean deleteUser(@PathVariable UUID id) {
        return userService.deleteUser(id);
    }
}