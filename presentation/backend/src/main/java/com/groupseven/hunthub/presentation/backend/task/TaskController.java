package com.groupseven.hunthub.presentation.backend.task;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groupseven.hunthub.domain.models.Task;
import com.groupseven.hunthub.domain.services.TaskService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired TaskService taskService;

    @GetMapping()
    public List<Task> getAllTasks() {
        return taskService.getAll();
    }
    
}
//crud