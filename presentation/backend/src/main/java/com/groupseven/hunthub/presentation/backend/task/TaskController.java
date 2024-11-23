package com.groupseven.hunthub.presentation.backend.task;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.groupseven.hunthub.presentation.backend.dto.response.TaskResponseDto;
import com.groupseven.hunthub.presentation.backend.dto.response.TaskDetailsResponseDto;
import com.groupseven.hunthub.domain.services.HunterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.groupseven.hunthub.presentation.backend.dto.request.CreateTaskDto;
import com.groupseven.hunthub.domain.models.PO;
import com.groupseven.hunthub.domain.models.Task;
import com.groupseven.hunthub.domain.models.Hunter;
import com.groupseven.hunthub.domain.services.POService;
import com.groupseven.hunthub.domain.services.TaskService;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final POService poService;
    private final TaskService taskService;
    private final HunterService hunterService;

    @Autowired
    public TaskController(POService poService, TaskService taskService, HunterService hunterService) {
        this.poService = poService;
        this.taskService = taskService;
        this.hunterService = hunterService;
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> getAllTasks() {
        List<Task> tasks = taskService.getAll();
        List<TaskResponseDto> taskResponseDtos = tasks.stream()
                .map(task -> new TaskResponseDto().convertToDTO(task))
                .toList();
        return ResponseEntity.ok(taskResponseDtos);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<TaskResponseDto>> getTasksByFilter(@RequestParam Map<String, String> filters) {
        Map<String, Object> parsedFilters = taskService.parseFilters(filters);

        List<Task> filteredTasks = taskService.findByFilter(parsedFilters);

        List<TaskResponseDto> taskResponseDtos = filteredTasks.stream()
                .map(task -> new TaskResponseDto().convertToDTO(task))
                .toList();

        return ResponseEntity.ok(taskResponseDtos);
    }

    @PostMapping("/{poId}")
    public ResponseEntity<TaskResponseDto> createTask(@RequestBody @Valid CreateTaskDto createTaskDto, @PathVariable UUID poId) {
        PO po = poService.findPOById(poId);
        if (po == null) {
            throw new IllegalArgumentException("PO not found.");
        }

        Task createdTask = taskService.createTask(
                poId,
                createTaskDto.getDescription(),
                createTaskDto.getTitle(),
                createTaskDto.getDeadline(),
                createTaskDto.getReward(),
                createTaskDto.getNumberOfMeetings(),
                createTaskDto.getNumberOfHuntersRequired(),
                createTaskDto.getRatingRequired(),
                createTaskDto.getTags()
        );

        TaskResponseDto response = new TaskResponseDto().convertToDTO(createdTask);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID id) {
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{taskId}/applying/{hunterId}")
    public ResponseEntity<String> applyHunterToTask(@PathVariable UUID taskId, @PathVariable UUID hunterId) {
        Task task = taskService.getTask(taskId);
        Hunter hunter = taskService.getHunter(hunterId);

            if (taskService.hasHunterApplied(task, hunter)) {
                throw new IllegalArgumentException("Hunter has already applied to the task.");
            }

            taskService.applyHunterToTask(task, hunter);
            return ResponseEntity.ok("Hunter applied to the task successfully.");

    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDetailsResponseDto> getTaskById(@PathVariable UUID id) {
        Task task = taskService.getTask(id);

        if (task == null) {
            throw new IllegalArgumentException("Task not found.");
        }
        TaskDetailsResponseDto taskDetailsResponseDto = TaskDetailsResponseDto.convertToTaskDetailsDTO(task);
        return ResponseEntity.ok(taskDetailsResponseDto);
    }

    @GetMapping("/po/{poId}")
    public ResponseEntity<List<TaskDetailsResponseDto>> getPoTasks(@PathVariable UUID poId) {
        List<Task> tasks = taskService.getTasksByPo(poId);

        if (tasks.isEmpty()) {
            throw new IllegalArgumentException("No tasks found for the given PO.");
        }

        List<TaskDetailsResponseDto> taskDetails = tasks.stream()
                .map(TaskDetailsResponseDto::convertToTaskDetailsDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(taskDetails);
    }

    @GetMapping("/hunter/{hunterId}")
    public ResponseEntity<List<TaskDetailsResponseDto>> getHunterTasks(@PathVariable UUID hunterId) {
        List<Task> tasks = taskService.getTasksByHunterId(hunterId);

        if (tasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<TaskDetailsResponseDto> taskDetails = tasks.stream()
                .map(TaskDetailsResponseDto::convertToTaskDetailsDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(taskDetails);
    }

    @PostMapping("/{taskId}/accept/{hunterId}")
    public ResponseEntity<String> acceptHunterForTask(@PathVariable UUID taskId, @PathVariable UUID hunterId) {
        Task task = taskService.getTask(taskId);
        Hunter hunter = taskService.getHunter(hunterId);

        if (task == null || hunter == null) {
            throw new IllegalArgumentException("Task or Hunter not found.");
        }

        taskService.acceptHunter(task, hunter);
        hunterService.acceptTask(hunter, task);
        return ResponseEntity.ok("Hunter accepted for the task successfully.");
    }

    @PostMapping("/{taskId}/decline/{hunterId}")
    public ResponseEntity<String> declineHunterForTask(@PathVariable UUID taskId, @PathVariable UUID hunterId) {
        Task task = taskService.getTask(taskId);
        Hunter hunter = taskService.getHunter(hunterId);

        if (task == null || hunter == null) {
            throw new IllegalArgumentException("Task or Hunter not found.");
        }

        taskService.declineHunter(task, hunter);
        return ResponseEntity.ok("Hunter declined for the task successfully.");
    }

    @PutMapping("/{taskId}/complete")
    public ResponseEntity<String> completeTask(@PathVariable UUID taskId) {
        Task task = taskService.getTask(taskId);

        if (task == null) {
            throw new IllegalArgumentException("Task not found.");
        }

        taskService.completeTask(task);
        return ResponseEntity.ok("Task completed successfully.");
    }

    @PostMapping("/hunter/{hunterId}/request-payment/{taskId}")
    public ResponseEntity<String> requestPayment(@PathVariable UUID hunterId, @PathVariable UUID taskId) {

        if (!hunterService.hunterRequestsPayment(hunterId, taskId)) {
            throw new IllegalArgumentException("Payment request failed.");
        }

        hunterService.payTheHunter(hunterId, taskId);
        return ResponseEntity.ok("Payment requested successfully.");
    }
}
