package com.groupseven.hunthub.presentation.backend.task;

import java.util.List;
import java.util.UUID;

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
import com.groupseven.hunthub.domain.services.POService;
import com.groupseven.hunthub.domain.services.TaskService;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    POService poService;

    @Autowired
    TaskService taskService;

    @GetMapping()
    public List<Task> getAllTasks() {
        return taskService.getAll();
    }

    @PostMapping("/{poId}")
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO, @PathVariable UUID poId) {
        System.out.println("Id do PO no parametro:" + poId);

        PO po = poService.findPOById(poId);

        System.out.println("Nome do PO:" + po.getName());
        System.out.println("Id do PO:" + po.getId().getId());

        if (po == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable UUID id, @RequestBody Task task) {
        TaskId taskId = new TaskId(id);
        task.setId(taskId);

        Task updatedTask = taskService.updateTask(id, task);

        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID id) {
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("{taskId}/applying/{hunterId}")
    public ResponseEntity<String> applyHunterToTask(@PathVariable UUID taskId, @PathVariable UUID hunterId) {
        try {
            if (taskService.getElements(taskId, hunterId) == 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task or Hunter not found.");
            } else {
                return ResponseEntity.ok("Hunter applied to the task successfully.");
            }
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
