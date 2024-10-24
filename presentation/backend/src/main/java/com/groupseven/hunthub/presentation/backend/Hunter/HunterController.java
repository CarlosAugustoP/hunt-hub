package com.groupseven.hunthub.presentation.backend.Hunter;

import com.groupseven.hunthub.domain.models.Hunter;
import com.groupseven.hunthub.domain.models.User;
import com.groupseven.hunthub.domain.models.UserId;
import com.groupseven.hunthub.domain.services.HunterService;
import com.groupseven.hunthub.domain.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/hunters")
public class HunterController {

    @Autowired
    private HunterService hunterService;

    @Autowired
    private UserService userService;

    @PostMapping("/register/{userId}")
    public User register(@RequestBody Hunter hunter, @PathVariable UUID userId) {
        hunter.setId(userId);
        System.out.println("Hunter: " + hunter);
        hunterService.createHunter(hunter);
        return hunter;
        }

        // hunterService.createHunter(hunter);

}



