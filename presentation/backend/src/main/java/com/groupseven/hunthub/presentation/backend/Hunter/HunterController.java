package com.groupseven.hunthub.presentation.backend.Hunter;

import com.groupseven.hunthub.domain.models.Hunter;
import com.groupseven.hunthub.presentation.backend.dto.request.CreateHunterDto;
import com.groupseven.hunthub.presentation.backend.dto.response.HunterDetailsResponseDto;
import com.groupseven.hunthub.presentation.backend.dto.response.HunterResponseDto;
import com.groupseven.hunthub.domain.services.HunterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/hunters")
public class HunterController {

    @Autowired
    private HunterService hunterService;

    @PostMapping()
    public ResponseEntity<HunterResponseDto> register(@RequestBody @Valid CreateHunterDto hunterDto) {
        try {
            Hunter hunter = hunterDto.convertToHunter();
            hunter.setRole("ROLE_HUNTER");
            Hunter createdHunter = hunterService.createHunter(hunter);
            HunterResponseDto hunterResponseDto = HunterResponseDto.convertToHunterDTO(createdHunter);
            return ResponseEntity.status(HttpStatus.CREATED).body(hunterResponseDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PreAuthorize("hasAuthority('ROLE_HUNTER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getHunterById(@PathVariable UUID id) {
        try {
            Hunter hunter = hunterService.findHunterById(id);
            HunterDetailsResponseDto hunterDetailsResponseDto = HunterDetailsResponseDto
                    .convertToHunterDetailsDto(hunter);
            return ResponseEntity.ok(hunterDetailsResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hunter not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }

    @GetMapping()
    public ResponseEntity<List<HunterResponseDto>> findAll() {
        try {
            List<Hunter> hunters = hunterService.getAllHunters();
            List<HunterResponseDto> hunterResponseDtos = HunterResponseDto.convertToHunterDTOList(hunters);
            return ResponseEntity.ok(hunterResponseDtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PreAuthorize("hasAuthority('ROLE_HUNTER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateHunter(@PathVariable UUID id, @RequestBody Hunter hunter) {
        try {
            hunter.setId(id);
            Hunter updatedHunter = hunterService.updateHunter(id, hunter);
            HunterResponseDto hunterResponseDto = HunterResponseDto.convertToHunterDTO(updatedHunter);
            return ResponseEntity.ok(hunterResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hunter not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }

    @PreAuthorize("hasAuthority('ROLE_HUNTER')")
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
