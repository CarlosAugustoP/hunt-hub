package com.groupseven.hunthub.presentation.backend.PO;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello-po")
public class HelloPoController {

    @PreAuthorize("hasAuthority('ROLE_PO')")
    @GetMapping
    public String helloPo() {
        return "Hello PO";
    }
}
