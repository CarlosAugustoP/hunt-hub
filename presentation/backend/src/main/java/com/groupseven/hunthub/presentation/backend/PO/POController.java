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

    @PutMapping("/{id}")
    public ResponseEntity<PO> updatePO(@PathVariable UUID id, @RequestBody PO po) {
        po.setId(id);
        System.out.println(po.getPoints());
        PO updatedPO = poService.updatePO(id, po);
        return ResponseEntity.ok(updatedPO);
    }

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



