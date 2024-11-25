package com.groupseven.hunthub.presentation.backend.notification;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import com.groupseven.hunthub.presentation.backend.dto.request.NotificationDto;
import com.groupseven.hunthub.persistence.jpa.models.NotificationJpa;
import com.groupseven.hunthub.persistence.jpa.repository.NotificationJpaRepository;
import com.groupseven.hunthub.presentation.backend.dto.response.NotificationResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/notifications")
public class NotificationController {

        @Autowired
        private NotificationJpaRepository notificationRepository;

        @PreAuthorize("hasAuthority('ROLE_HUNTER')")
        @GetMapping("/hunter/{id}")
        public ResponseEntity<List<NotificationDto>> getNotificationsByHunter(@PathVariable UUID id) {
                List<NotificationJpa> notifications = notificationRepository.findByHunterId(id);

                List<NotificationDto> response = notifications.stream()
                                .map(n -> new NotificationDto(
                                                n.getId(),
                                                n.getTheme(),
                                                n.getMessage(),
                                                n.getCreatedAt(),
                                                n.getHunterId(),
                                                n.getPoId(),
                                                n.getTaskId()))
                                .collect(Collectors.toList());

                return ResponseEntity.ok(response);
        }

        @PreAuthorize("hasAuthority('ROLE_PO')")
        @GetMapping("/po/{id}")
        public ResponseEntity<List<NotificationDto>> getNotificationsByPo(@PathVariable UUID id) {
                List<NotificationJpa> notifications = notificationRepository.findByPoId(id);

                List<NotificationDto> response = notifications.stream()
                                .map(n -> new NotificationDto(
                                                n.getId(),
                                                n.getTheme(),
                                                n.getMessage(),
                                                n.getCreatedAt(),
                                                n.getHunterId(),
                                                n.getPoId(),
                                                n.getTaskId()))
                                .collect(Collectors.toList());

                return ResponseEntity.ok(response);
        }

        @GetMapping("/{id}")
        public ResponseEntity<NotificationResponseDto> getNotificationById(@PathVariable UUID id) {
                return notificationRepository.findById(id)
                                .map(notificationJpa -> new NotificationResponseDto(
                                                notificationJpa.getTheme(),
                                                notificationJpa.getMessage(),
                                                notificationJpa.getCreatedAt(),
                                                notificationJpa.getTaskId()))
                                .map(ResponseEntity::ok)
                                .orElse(ResponseEntity.notFound().build());
        }

}
