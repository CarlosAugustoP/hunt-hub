package com.groupseven.hunthub.domain.repository;

import java.util.List;
import java.util.UUID;

import com.groupseven.hunthub.domain.models.Hunter;
import com.groupseven.hunthub.domain.models.PO;
import com.groupseven.hunthub.domain.models.Task;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository {

    public void save(Task task);

    public Task findById(UUID id);

    public List<Task> findAll();

    public void delete(UUID id);

    public void applyHunterToTask(UUID taskId, Hunter hunter);

    public void acceptHunter(UUID taskId, Hunter hunter);

    public void declineHunter(UUID taskId, Hunter hunter);

    public List<Task> findTasksNotAppliedByHunter(UUID hunterId);

    public List<Task> findTasksByPoId(UUID poId);

    public List<Task> findTasksByHunterId(UUID hunterId);
}