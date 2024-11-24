package com.groupseven.hunthub.presentation.backend.rating;

import com.groupseven.hunthub.presentation.backend.dto.response.HunterResponseDto;
import com.groupseven.hunthub.presentation.backend.dto.response.PoResponseDto;
import com.groupseven.hunthub.domain.services.POService;
import com.groupseven.hunthub.domain.services.RatingCheckService;
import com.groupseven.hunthub.domain.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.groupseven.hunthub.domain.models.*;
import com.groupseven.hunthub.domain.services.HunterService;
import com.groupseven.hunthub.presentation.backend.dto.request.CreateRatingDto;

import java.util.UUID;

@RestController
@RequestMapping("/api/rating")
public class RatingController {

        private @Autowired HunterService hunterService;
        private @Autowired POService poService;
        private @Autowired TaskService taskService;
        private @Autowired RatingCheckService ratingCheckService;

        @PostMapping("/hunter/{hunterId}/po/{poId}/task/{taskId}")
        public ResponseEntity<PoResponseDto> hunterRatePO(@PathVariable UUID hunterId,
                        @PathVariable UUID poId,
                        @PathVariable UUID taskId,
                        @RequestBody @Valid CreateRatingDto rating) {

                PO ratedPO = poService.findPOById(poId);
                if (ratedPO == null)
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

                RatingCheck ratingCheck = new RatingCheck(new UserId(hunterId), new TaskId(taskId), new UserId(poId));
                ratingCheckService.save(ratingCheck);
                Task task = taskService.getTaskById(taskId);
                hunterService.ratePO(ratedPO, rating.getRating(), task);
                poService.save(ratedPO);

                PoResponseDto responseDto = PoResponseDto.convertToPODTO(ratedPO);

                return ResponseEntity.ok(responseDto);

        }

        @PostMapping("/po/{poId}/hunter/{hunterId}")
        public ResponseEntity<HunterResponseDto> poRateHunter(@PathVariable UUID poId, @PathVariable UUID hunterId,
                        @RequestBody @Valid CreateRatingDto rating) {

                Hunter ratedHunter = hunterService.findHunterById(hunterId);
                if (ratedHunter == null)
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

                poService.rateHunter(ratedHunter, rating.getRating());
                hunterService.save(ratedHunter);

                HunterResponseDto responseDto = HunterResponseDto.convertToHunterDTO(ratedHunter);

                return ResponseEntity.ok(responseDto);
        }

        @PostMapping("/hunter/{ratingHunterId}/rate/{ratedHunterId}/task/{taskId}")
        public ResponseEntity<HunterResponseDto> hunterRateHunter(@PathVariable UUID ratingHunterId,
                        @PathVariable UUID ratedHunterId,
                        @PathVariable UUID taskId,
                        @RequestBody @Valid CreateRatingDto rating) {

                Hunter ratingHunter = hunterService.findHunterById(ratingHunterId);
                Hunter ratedHunter = hunterService.findHunterById(ratedHunterId);

                Task task = taskService.getTaskById(taskId);

                RatingCheck ratingCheck = new RatingCheck(new UserId(ratingHunterId), new TaskId(taskId),
                                new UserId(ratedHunterId));
                ratingCheckService.save(ratingCheck);

                hunterService.rateHunter(ratingHunter, ratedHunter, rating.getRating(), task);
                hunterService.save(ratedHunter);

                HunterResponseDto responseDto = HunterResponseDto.convertToHunterDTO(ratedHunter);

                return ResponseEntity.ok(responseDto);

        }
}
