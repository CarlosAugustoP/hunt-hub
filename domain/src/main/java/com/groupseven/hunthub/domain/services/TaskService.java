package com.groupseven.hunthub.domain.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.groupseven.hunthub.domain.repository.TaskRepository;
import com.groupseven.hunthub.domain.models.Task;
import com.groupseven.hunthub.domain.models.PO;
import java.util.Date;


@Service
public class TaskService {

    private final TaskRepository taskRepository;

    
    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void createTask(PO po ,
                        String name, 
                        String description,
                        String title,
                        Date deadline,
                        int reward, 
                        int numberOfMeetings,
                        int numberOfHuntersRequired
                        ){
        try {
            if (po.getPoints() < numberOfHuntersRequired * reward ){
                throw new IllegalArgumentException("Not enough points");
            }  
                po.setPoints(po.getPoints() - numberOfHuntersRequired * reward);
                Task task = new Task(po, description, title, deadline, reward, numberOfMeetings, numberOfHuntersRequired);
                task.setPo(po);
                taskRepository.save(task);   
            } 
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred");
        }
        
    }
}


