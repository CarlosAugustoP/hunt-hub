package com.groupseven.hunthub.domain.services;

import com.groupseven.hunthub.domain.repository.TaskRepository;
import com.groupseven.hunthub.domain.models.Task;
import com.groupseven.hunthub.domain.models.PO;

import java.util.Date;

public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void createTask(PO po,
                           String name,
                           String description,
                           String title,
                           Date deadline,
                           int reward,
                           int numberOfMeetings,
                           int numberOfHuntersRequired) {
        try {
            if (po.getPoints() < numberOfHuntersRequired * reward) {
                throw new IllegalArgumentException("Not enough points");
            }
            po.setPoints(po.getPoints() - numberOfHuntersRequired * reward);

            Task task = new Task(po, description, title, deadline, reward, numberOfMeetings, numberOfHuntersRequired);
            task.setPo(po);

            taskRepository.save(task);

            po.addTask(task);  
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred");
        }
    }
}
