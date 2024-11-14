package com.groupseven.hunthub.presentation.backend.Hunter;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello-hunter")
public class HelloHunterController {

    @PreAuthorize("hasAuthority('ROLE_HUNTER')")
    @GetMapping
    public String helloHunter() {
        return "Hello Hunter";
    }
}
