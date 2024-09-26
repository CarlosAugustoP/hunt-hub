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

            // Criando a nova Task
            Task task = new Task(po, description, title, deadline, reward, numberOfMeetings, numberOfHuntersRequired);
            task.setPo(po);

            // Salvando a Task no repositório
            taskRepository.save(task);

            // Adicionando a task na lista de tasks do PO
            po.addTask(task);  // Certifique-se de que o PO tenha esse método
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred");
        }
    }
}
