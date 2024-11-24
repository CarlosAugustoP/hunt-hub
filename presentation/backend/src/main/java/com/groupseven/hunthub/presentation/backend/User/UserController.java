package com.groupseven.hunthub.presentation.backend.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.groupseven.hunthub.domain.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import com.groupseven.hunthub.domain.models.User;
import com.groupseven.hunthub.presentation.backend.dto.request.AuthenticationDTO;
import com.groupseven.hunthub.domain.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO authDTO) {
        try {
            var userNamePassword = new UsernamePasswordAuthenticationToken(authDTO.getEmail(), authDTO.getPassword());
            var auth = this.authenticationManager.authenticate(userNamePassword);
            var user = (User) auth.getPrincipal();
            var token = tokenService.generateToken(user);

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("id", user.getId().getId());
            response.put("role", user.getRole());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Credenciais inválidas");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    /*
     * Esse método vai dizer que não tem id no retorno do JSON, mas não se
     * preocupar, no banco aparece tudo certo.
     */
    @PostMapping()
    public UUID register(@RequestBody User user) {
        userService.createUser(user, user.getRole());
        return user.getId().getId();
    }

    // @GetMapping("/{id}")
    // public User getUser(@PathVariable String id) {
    // return userService.findUserById(id);
    // }
    //
    // @DeleteMapping("/{id}")
    // public boolean deleteUser(@PathVariable UUID id) {
    // return userService.deleteUser(id);
    // }
}