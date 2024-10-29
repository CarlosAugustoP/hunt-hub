package com.groupseven.hunthub.presentation.backend.task;

import com.groupseven.hunthub.domain.models.PO;
import com.groupseven.hunthub.domain.models.Task;
import com.groupseven.hunthub.domain.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.groupseven.hunthub.domain.services.POService;

import java.util.List;
import java.util.UUID;

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
        PO po = poService.findPOById(poId);

        if (po == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Task task = taskService.createTask(
                po,
                taskDTO.getName(),
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


//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteTask(@PathVariable UUID id) {
//        taskService.deleteTask(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

}