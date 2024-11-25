package com.groupseven.hunthub.presentation.backend.PO;

import com.groupseven.hunthub.presentation.backend.dto.request.CreatePoDto;
import com.groupseven.hunthub.presentation.backend.dto.request.UpdatePointsDto;
import com.groupseven.hunthub.domain.models.PO;
import com.groupseven.hunthub.presentation.backend.dto.response.PoResponseDto;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import com.groupseven.hunthub.presentation.backend.dto.response.PoDetailsResponseDto;
import com.groupseven.hunthub.domain.services.POService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/po")
public class POController {

        @Autowired
        private POService poService;

        @PostMapping
        public ResponseEntity<PoResponseDto> register(@RequestBody @Valid CreatePoDto createPODto) {
                PO po = createPODto.convertToPO();
                po.setRole("ROLE_PO");
                PO createdPO = poService.createPO(po);
                PoResponseDto poResponseDto = PoResponseDto.convertToPODTO(createdPO);
                return ResponseEntity.status(HttpStatus.CREATED).body(poResponseDto);
        }

        @SecurityRequirement(name = "bearerAuth")
        @PreAuthorize("hasAuthority('ROLE_PO')")
        @GetMapping("/{id}")
        public ResponseEntity<?> getPoById(@PathVariable UUID id) {
                PO po = poService.findPOById(id);
                PoDetailsResponseDto poDetailsDto = PoDetailsResponseDto.convertToPoDetailsDto(po);
                return ResponseEntity.ok(poDetailsDto);
        }

        @SecurityRequirement(name = "bearerAuth")
        @PreAuthorize("hasAuthority('ROLE_PO')")
        @PutMapping("/{id}")
        public ResponseEntity<?> updatePO(@PathVariable UUID id, @RequestBody PO po) {
                po.setId(id);
                PO updatedPO = poService.updatePO(id, po);
                PoResponseDto poResponseDto = PoResponseDto.convertToPODTO(updatedPO);
                return ResponseEntity.ok(poResponseDto);
        }

        @SecurityRequirement(name = "bearerAuth")
        @PreAuthorize("hasAuthority('ROLE_PO')")
        @DeleteMapping("/{id}")
        public ResponseEntity<?> deletePO(@PathVariable UUID id) {
                poService.deletePO(id);
                return ResponseEntity.noContent().build();
        }

        @GetMapping
        public ResponseEntity<?> getAllPOs() {
                List<PO> poList = poService.getAllPOs();
                List<PoResponseDto> poResponseDtoList = PoResponseDto.convertToPODTOList(poList);
                return ResponseEntity.ok(poResponseDtoList);
        }

        @SecurityRequirement(name = "bearerAuth")
        @PreAuthorize("hasAuthority('ROLE_PO')")
        @PutMapping("/points/{poId}")
        public ResponseEntity<String> updatePoints(
                        @PathVariable UUID poId,
                        @RequestBody @Valid UpdatePointsDto updatePointsDto) {
                PO po = poService.findPOById(poId);
                po.setPoints(po.getPoints() + updatePointsDto.getPoints());
                poService.save(po);
                return ResponseEntity.ok("Points updated successfully.");
        }

}
