package com.groupseven.hunthub.presentation.backend.Hunter;

import com.groupseven.hunthub.domain.models.Hunter;
import com.groupseven.hunthub.domain.models.dto.HunterDetailsDto;
import com.groupseven.hunthub.domain.models.dto.HunterDto;
import com.groupseven.hunthub.domain.services.HunterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/hunters")
public class HunterController {

    @Autowired
    private HunterService hunterService;

    @PostMapping()
    public ResponseEntity<HunterDto> register(@RequestBody Hunter hunter) {
        try {
            hunter.setRole("ROLE_HUNTER");
            Hunter createdHunter = hunterService.createHunter(hunter);
            HunterDto hunterDto = HunterDto.convertToHunterDTO(createdHunter);
            return ResponseEntity.status(HttpStatus.CREATED).body(hunterDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getHunterById(@PathVariable UUID id) {
        try {
            Hunter hunter = hunterService.findHunterById(id);

            HunterDetailsDto hunterDetailsDto = HunterDetailsDto.convertToHunterDetailsDto(hunter);

            return ResponseEntity.ok(hunterDetailsDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hunter not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }


    @GetMapping()
    public ResponseEntity<List<HunterDto>> findAll() {
        try {
            List<Hunter> hunters = hunterService.getAllHunters();
            List<HunterDto> hunterDtos = HunterDto.convertToHunterDTOList(hunters);
            return ResponseEntity.ok(hunterDtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateHunter(@PathVariable UUID id, @RequestBody Hunter hunter) {
        try {
            hunter.setId(id);
            Hunter updatedHunter = hunterService.updateHunter(id, hunter);
            HunterDto hunterDto = HunterDto.convertToHunterDTO(updatedHunter);
            return ResponseEntity.ok(hunterDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hunter not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHunter(@PathVariable UUID id) {
        try {
            Hunter hunter = hunterService.findHunterById(id);
            hunterService.deleteHunter(hunter);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hunter not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }
}
