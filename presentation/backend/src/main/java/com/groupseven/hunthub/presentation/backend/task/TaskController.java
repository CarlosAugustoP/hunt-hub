package com.groupseven.hunthub.presentation.backend.task;

import java.util.List;
import java.util.UUID;

import com.groupseven.hunthub.domain.models.dto.TaskDTO;
import com.groupseven.hunthub.domain.models.dto.TaskDetailsDto;
import com.groupseven.hunthub.domain.services.HunterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groupseven.hunthub.domain.models.PO;
import com.groupseven.hunthub.domain.models.Task;
import com.groupseven.hunthub.domain.models.TaskId;
import com.groupseven.hunthub.domain.models.Hunter;
import com.groupseven.hunthub.domain.services.POService;
import com.groupseven.hunthub.domain.services.TaskService;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    POService poService;

    @Autowired
    TaskService taskService;

    @Autowired
    HunterService hunterService;

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        try {
            List<Task> tasks = taskService.getAll();
            List<TaskDTO> taskDtos = tasks.stream().map(task -> new TaskDTO().convertToDTO(task)).toList();
            return ResponseEntity.ok(taskDtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @PostMapping("/{poId}")
    public ResponseEntity<?> createTask(@RequestBody TaskDTO taskDTO, @PathVariable UUID poId) {
        try {
            PO po = poService.findPOById(poId);

            if (po == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("PO not found.");
            }

            Task task = taskService.createTask(
                    po.getId().getId(),
                    taskDTO.getDescription(),
                    taskDTO.getTitle(),
                    taskDTO.getDeadline(),
                    taskDTO.getReward(),
                    taskDTO.getNumberOfMeetings(),
                    taskDTO.getNumberOfHuntersRequired(),
                    taskDTO.getRatingRequired(),
                    taskDTO.getTags()
            );

            TaskDTO responseDTO = taskDTO.convertToDTO(task);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID id) {
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{taskId}/applying/{hunterId}")
    public ResponseEntity<String> applyHunterToTask(@PathVariable UUID taskId, @PathVariable UUID hunterId) {
        try {
            Task task = taskService.getTask(taskId);
            Hunter hunter = taskService.getHunter(hunterId);

            if (task != null && hunter != null) {
                taskService.applyHunterToTask(task, hunter);
                return ResponseEntity.ok("Hunter applied to the task successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task or Hunter not found.");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable UUID id) {
        try {
            Task task = taskService.getTask(id);
            if (task == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");
            }
            TaskDetailsDto taskDetailsDto = TaskDetailsDto.convertToTaskDetailsDTO(task);
            return ResponseEntity.ok(taskDetailsDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }



    @PostMapping("/{taskId}/accept/{hunterId}")
    public ResponseEntity<String> acceptHunterForTask(@PathVariable UUID taskId, @PathVariable UUID hunterId) {
        try {
            Task task = taskService.getTask(taskId);
            Hunter hunter = taskService.getHunter(hunterId);

            if (task != null && hunter != null) {
                taskService.acceptHunter(task, hunter);
                return ResponseEntity.ok("Hunter accepted for the task successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task or Hunter not found.");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }

    @PostMapping("/{taskId}/decline/{hunterId}")
    public ResponseEntity<String> declineHunterForTask(@PathVariable UUID taskId, @PathVariable UUID hunterId) {
        try {
            Task task = taskService.getTask(taskId);
            Hunter hunter = taskService.getHunter(hunterId);

            if (task != null && hunter != null) {
                taskService.declineHunter(task, hunter);
                return ResponseEntity.ok("Hunter declined for the task successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task or Hunter not found.");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }

    @PostMapping("/{taskId}/complete")
    public ResponseEntity<String> completeTask(@PathVariable UUID taskId) {
        try {
            Task task = taskService.getTask(taskId);

            if (task != null) {
                taskService.completeTask(task);
                return ResponseEntity.ok("Task completed successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }

    @PostMapping("/hunter/{hunterId}/request-payment/{taskId}")
    public ResponseEntity<String> requestPayment(@PathVariable UUID hunterId, @PathVariable UUID taskId) {
        try {
            if (hunterService.hunterRequestsPayment(hunterId, taskId)) {
                hunterService.payTheHunter(hunterId, taskId);
                return ResponseEntity.ok("Payment requested successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment request failed.");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }



}
