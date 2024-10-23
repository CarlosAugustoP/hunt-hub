package com.groupseven.hunthub.presentation.backend.User;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import com.groupseven.hunthub.domain.models.User;
import com.groupseven.hunthub.domain.models.AuthenticationDTO;
import com.groupseven.hunthub.domain.services.UserService;
import com.groupseven.hunthub.persistence.jpa.repository.UserRepositoryImpl;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO authDTO) {
        var userNamePassword = new UsernamePasswordAuthenticationToken(authDTO.email(), authDTO.password());
        var auth = this.authenticationManager.authenticate(userNamePassword);
        return ResponseEntity.ok(auth);
    }

    /* Esse método vai dizer que não tem id no retorno do JSON, mas não se preocupar, no banco aparece tudo certo. */
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        userService.createUser(user);
        return user;
    }

//    @GetMapping("/{id}")
//    public User getUser(@PathVariable String id) {
//        return userService.findUserById(id);
//    }
//
//    @DeleteMapping("/{id}")
//    public boolean deleteUser(@PathVariable UUID id) {
//        return userService.deleteUser(id);
//    }
}