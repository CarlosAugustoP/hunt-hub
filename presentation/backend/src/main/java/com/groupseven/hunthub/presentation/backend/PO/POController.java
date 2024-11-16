package com.groupseven.hunthub.presentation.backend.PO;

import com.groupseven.hunthub.domain.models.Hunter;
import com.groupseven.hunthub.domain.models.PO;
import com.groupseven.hunthub.domain.models.User;
import com.groupseven.hunthub.domain.models.dto.PODto;
import com.groupseven.hunthub.domain.models.dto.PoDetailsDto;
import com.groupseven.hunthub.domain.services.HunterService;
import com.groupseven.hunthub.domain.services.POService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/po")
public class POController {

    @Autowired
    private POService poService;

    @PostMapping
    public ResponseEntity<?> register(@RequestBody PO po) {
        try {
            po.setRole("ROLE_PO");
            PO createdPO = poService.createPO(po);

            PODto poDto = PODto.convertToPODTO(createdPO);

            return ResponseEntity.status(HttpStatus.CREATED).body(poDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create PO: " + e.getMessage());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getPoById(@PathVariable UUID id) {
        try {
            PO po = poService.findPOById(id);
            PoDetailsDto poDetailsDto = PoDetailsDto.convertToPoDetailsDto(po);
            return ResponseEntity.ok(poDetailsDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("PO not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while retrieving the PO: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePO(@PathVariable UUID id, @RequestBody PO po) {
        try {
            po.setId(id);
            PO updatedPO = poService.updatePO(id, po);

            PODto poDto = PODto.convertToPODTO(updatedPO);

            return ResponseEntity.ok(poDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("PO not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the PO: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePO(@PathVariable UUID id) {
        try {
            poService.deletePO(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("PO not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the PO: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllPOs() {
        try {
            List<PO> poList = poService.getAllPOs();

            List<PODto> poDtoList = PODto.convertToPODTOList(poList);

            return ResponseEntity.ok(poDtoList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while retrieving the POs: " + e.getMessage());
        }
    }
}
