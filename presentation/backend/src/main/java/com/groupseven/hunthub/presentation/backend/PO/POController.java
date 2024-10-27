package com.groupseven.hunthub.presentation.backend.PO;

import com.groupseven.hunthub.domain.models.Hunter;
import com.groupseven.hunthub.domain.models.PO;
import com.groupseven.hunthub.domain.models.User;
import com.groupseven.hunthub.domain.services.HunterService;
import com.groupseven.hunthub.domain.services.POService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/po")
public class POController {

    @Autowired
    private POService poService;

    @PostMapping()
    public User register(@RequestBody PO po) {
        poService.createPO(po);
        return po;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PO> getHunterById(@PathVariable UUID id) {
        return ResponseEntity.ok(poService.findPOById(id));
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Hunter> updateHunter(@PathVariable UUID id, @RequestBody Hunter hunter) {
//        hunter.setId(id);
//        Hunter updatedHunter = poService.(id, hunter);
//        return ResponseEntity.ok(updatedHunter);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHunter(@PathVariable UUID id) {
        poService.deletePO(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity<List<PO>> getAllPo() {
        return ResponseEntity.ok(poService.getAllPOs());
    }
}



