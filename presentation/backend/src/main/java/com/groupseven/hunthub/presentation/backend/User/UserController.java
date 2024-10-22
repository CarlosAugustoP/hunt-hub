package com.groupseven.hunthub.presentation.backend.User;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.groupseven.hunthub.domain.models.User;
import com.groupseven.hunthub.domain.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.createUser(user.getName(), user.getEmail(), user.getPassword(), user.getCpf());
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