package com.groupseven.hunthub.persistence.jpa.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.groupseven.hunthub.domain.models.PO;
import org.springframework.stereotype.Component;

import com.groupseven.hunthub.domain.models.Tags;
import com.groupseven.hunthub.domain.models.Task;
import com.groupseven.hunthub.domain.models.TaskId;
import com.groupseven.hunthub.persistence.jpa.models.TagsJPA;
import com.groupseven.hunthub.persistence.jpa.models.TaskJpa;
import com.groupseven.hunthub.persistence.jpa.mapper.POMapper;

@Component
public class TaskMapper {
    POMapper poMapper = new POMapper();

    public TaskJpa toEntity(Task task) {
        TaskJpa taskJpa = new TaskJpa();
        taskJpa.setPo(poMapper.toEntity(task.getPo()));
        taskJpa.setId(task.getId().getId());
        taskJpa.setTitle(task.getTitle());
        taskJpa.setDescription(task.getDescription());
        taskJpa.setStatus(TaskStatusMapper.toEntity(task.getStatus()));
        taskJpa.setDeadline(task.getDeadline());
        taskJpa.setReward(task.getReward());
        taskJpa.setNumberOfMeetings(task.getNumberOfMeetings());
        taskJpa.setNumberOfHuntersRequired(task.getNumberOfHuntersRequired());
        taskJpa.setRatingRequired(task.getRatingRequired());

        List<TagsJPA> tagsJpa = task.getTags().stream()
                .map(TagsMapper::toEntity)
                .collect(Collectors.toList());
        taskJpa.setTagsJPA(tagsJpa);

        return taskJpa;
    }

    public Task toDomain(TaskJpa taskJpa) {
        Task task = new Task();
        TaskId taskId = new TaskId(taskJpa.getId());
        task.setTitle(taskJpa.getTitle());
        task.setId(taskId);
        task.setDescription(taskJpa.getDescription());
        task.setStatus(TaskStatusMapper.toDomain(taskJpa.getStatus()));
        task.setDeadline(taskJpa.getDeadline());
        task.setReward(taskJpa.getReward());
        task.setNumberOfMeetings(taskJpa.getNumberOfMeetings());
        task.setNumberOfHuntersRequired(taskJpa.getNumberOfHuntersRequired());
        task.setRatingRequired(taskJpa.getRatingRequired());

        List<Tags> tagsDomain = taskJpa.getTagsJPA().stream()
                .map(TagsMapper::toDomain)
                .collect(Collectors.toList());
        task.setTags(tagsDomain);

        return task;
    }
}