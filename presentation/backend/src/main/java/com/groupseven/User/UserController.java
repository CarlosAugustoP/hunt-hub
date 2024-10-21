package com.groupseven.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.groupseven.hunthub.persistence.jpa.repository.UserRepositoryImpl;
import com.groupseven.hunthub.domain.repository.PoRepository;
import com.groupseven.hunthub.domain.services.UserService;
@RestController
@RequestMapping("/user")
/* Initial blueprint for a controller. This does not reflect how they will actually turn out. */
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getUser() {
        return "user";
    }
}
