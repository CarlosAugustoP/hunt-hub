package com.groupseven.hunthub.presentation.backend.Hunter;

import com.groupseven.hunthub.domain.models.Hunter;
import com.groupseven.hunthub.domain.models.User;
import com.groupseven.hunthub.domain.services.HunterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/hunters")
public class HunterController {

    @Autowired
    private HunterService hunterService;

    @PostMapping()
    public User register(@RequestBody Hunter hunter) {
        hunterService.createHunter(hunter);
        return hunter;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hunter> getHunterById(@PathVariable UUID id) {
        return ResponseEntity.ok(hunterService.findHunterById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hunter> updateHunter(@PathVariable UUID id, @RequestBody Hunter hunter) {
        Hunter updatedHunter = hunterService.updateHunter(id, hunter);
        return ResponseEntity.ok(updatedHunter);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHunter(@PathVariable UUID id) {
        Hunter hunter = hunterService.findHunterById(id);
        hunterService.deleteHunter(hunter);
        return ResponseEntity.noContent().build();
    }
}



