package com.groupseven.hunthub.presentation.backend.rating;

import com.groupseven.hunthub.domain.services.POService;
import org.springframework.beans.factory.annotation.Autowired;
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


    @PostMapping("/hunter/{hunterId}/po/{poId}")
    public ResponseEntity<PO> hunterRatePO(@PathVariable UUID hunterId, @PathVariable UUID poId, @RequestBody int rating){
        PO ratedPO = poService.findPOById(poId);
        hunterService.ratePO(ratedPO, rating);
        poService.save(ratedPO);
        return ResponseEntity.ok(ratedPO);
    }

    @PostMapping("/po/{poId}/hunter/{hunterId}")
    public ResponseEntity<Hunter> poRateHunter(@PathVariable UUID poId, @PathVariable UUID hunterId, @RequestBody int rating){
        Hunter ratedHunter = hunterService.findHunterById(hunterId);
        poService.rateHunter(ratedHunter, rating);
        hunterService.save(ratedHunter);
        return ResponseEntity.ok(ratedHunter);
    }

    @PostMapping("/hunter/{ratingHunterId}/rate/{ratedHunterId}")
    public ResponseEntity<Hunter> hunterRateHunter(@PathVariable UUID ratingHunterId, @PathVariable UUID ratedHunterId, @RequestBody int rating){
        Hunter ratingHunter = hunterService.findHunterById(ratingHunterId);
        Hunter ratedHunter = hunterService.findHunterById(ratedHunterId);
        hunterService.rateHunter(ratingHunter, ratedHunter, rating);
        hunterService.save(ratedHunter);
        return ResponseEntity.ok(ratedHunter);
    }
}
