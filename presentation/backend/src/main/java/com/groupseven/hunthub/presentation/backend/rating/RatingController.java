package com.groupseven.hunthub.presentation.backend.rating;

import com.groupseven.hunthub.domain.models.dto.HunterDto;
import com.groupseven.hunthub.domain.models.dto.PODto;
import com.groupseven.hunthub.domain.services.POService;
import com.groupseven.hunthub.domain.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.groupseven.hunthub.domain.models.*;
import com.groupseven.hunthub.domain.services.HunterService;
import com.groupseven.hunthub.persistence.jpa.repository.HunterRepositoryImpl;
import com.groupseven.hunthub.persistence.jpa.repository.PORepositoryImpl;
import java.util.UUID;

@RestController
@RequestMapping("/rating")
public class RatingController {

    private @Autowired HunterService hunterService;
    private @Autowired POService poService;
    private @Autowired TaskService taskService;


    @PostMapping("/hunter/{hunterId}/po/{poId}/task/{taskId}")
    public ResponseEntity<PODto> hunterRatePO(@PathVariable UUID hunterId,
                                              @PathVariable UUID poId,
                                              @PathVariable UUID taskId,
                                              @RequestBody int rating) {
        try {
            PO ratedPO = poService.findPOById(poId);
            if (ratedPO == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            if (ratedPO.getRole() == null || ratedPO.getRole().isEmpty()) {
                ratedPO.setRole("ROLE_PO");
            }

            hunterService.ratePO(ratedPO, rating);
            poService.save(ratedPO);
            Hunter payedHunter = hunterService.payTheHunter(hunterId, taskId);
            hunterService.save(payedHunter);
            System.out.println("Hunter points "+ payedHunter.getPoints());
            PODto responseDto = PODto.convertToPODTO(ratedPO);

            return ResponseEntity.ok(responseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            System.out.println("erro" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @PostMapping("/po/{poId}/hunter/{hunterId}")
    public ResponseEntity<HunterDto> poRateHunter(@PathVariable UUID poId, @PathVariable UUID hunterId, @RequestBody int rating) {
        try {
            Hunter ratedHunter = hunterService.findHunterById(hunterId);

            if (ratedHunter == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            if (ratedHunter.getRole() == null || ratedHunter.getRole().isEmpty()) {
                ratedHunter.setRole("ROLE_HUNTER");
            }

            poService.rateHunter(ratedHunter, rating);

            hunterService.save(ratedHunter);

            HunterDto responseDto = HunterDto.convertToHunterDTO(ratedHunter);

            return ResponseEntity.ok(responseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/hunter/{ratingHunterId}/rate/{ratedHunterId}")
    public ResponseEntity<HunterDto> hunterRateHunter(@PathVariable UUID ratingHunterId, @PathVariable UUID ratedHunterId, @RequestBody int rating) {
        try {
            Hunter ratingHunter = hunterService.findHunterById(ratingHunterId);
            Hunter ratedHunter = hunterService.findHunterById(ratedHunterId);

            if (ratingHunter == null || ratedHunter == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            if (ratedHunter.getRole() == null || ratedHunter.getRole().isEmpty()) {
                ratedHunter.setRole("ROLE_HUNTER");
            }

            hunterService.rateHunter(ratingHunter, ratedHunter, rating);

            hunterService.save(ratedHunter);

            HunterDto responseDto = HunterDto.convertToHunterDTO(ratedHunter);

            return ResponseEntity.ok(responseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
