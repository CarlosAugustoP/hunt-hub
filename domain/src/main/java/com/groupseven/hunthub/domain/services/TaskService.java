package com.groupseven.hunthub.domain.services;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.groupseven.hunthub.domain.models.Notification;
import org.springframework.stereotype.Service;

import com.groupseven.hunthub.domain.models.PO;
import com.groupseven.hunthub.domain.models.Task;
import com.groupseven.hunthub.domain.repository.TaskRepository;

@Service
public class TaskService {

    private TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskService() {

    }

    public void createTask(PO po,
                           String name,
                           String description,
                           String title,
                           Date deadline,
                           int reward,
                           int numberOfMeetings,
                           int numberOfHuntersRequired,
                           double ratingRequired) {
        if (po.getPoints() < numberOfHuntersRequired * reward) {
            throw new IllegalArgumentException("Not enough points");
        }
        po.setPoints(po.getPoints() - numberOfHuntersRequired * reward);

        Task task = new Task(po, description, title, deadline, reward, numberOfMeetings, numberOfHuntersRequired, ratingRequired);
        task.setPo(po);

        taskRepository.save(task);

        po.addTask(task);
    }

    public List<Task> findByFilter(Map<String, Object> filters) {
        List<Task> filteredTasks = taskRepository.findAll();

        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            String filter = entry.getKey();
            Object value = entry.getValue();

            switch (filter) {
                case "reward" -> filteredTasks.removeIf(task -> task.getReward() <= (int) value);
                case "meetings" -> filteredTasks.removeIf(task -> task.getNumberOfMeetings() <= (int) value);
                case "ratingRequired" -> filteredTasks.removeIf(task -> task.getRatingRequired() < (int) value);
                case "PORating" -> filteredTasks.removeIf(task -> task.getPo().getRating() > (int) value);
                case "numberOfHuntersRequired" ->
                        filteredTasks.removeIf(task -> task.getNumberOfHuntersRequired() > (int) value);
                default -> {
                    // Filtro desconhecido ou não suportado
                }
            }
        }

        return filteredTasks;
    }

}
