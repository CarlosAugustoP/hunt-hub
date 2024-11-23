package com.groupseven.hunthub.steps.builder;

import com.groupseven.hunthub.domain.models.Tags;
import com.groupseven.hunthub.domain.models.Task;
import com.groupseven.hunthub.domain.models.TaskId;
import com.groupseven.hunthub.domain.models.TaskStatus;
import com.groupseven.hunthub.steps.interfaces.TaskBuilder;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.UUID;

public class BasicTaskBuilder implements TaskBuilder {

    private Task task;

    public BasicTaskBuilder() {
        this.task = new Task();
    }

    @Override
    public void buildDescription() {
        task.setDescription("Sample task description");
    }

    @Override
    public void buildTitle() {
        task.setTitle("Sample task title");
    }

    @Override
    public void buildTaskId() {
        task.setId(new TaskId(UUID.randomUUID()));
    }

    @Override
    public void buildStatus() {
        task.setStatus(TaskStatus.PENDING);
    }

    @Override
    public void buildReward() {
        task.setReward(100);
    }

    @Override
    public void buildNumberOfHuntersRequired() {
        task.setNumberOfHuntersRequired(2);
    }

    @Override
    public void buildRatingRequired() {
        task.setRatingRequired(4.5);
    }

    @Override
    public void buildTags() {
        task.setTags(Arrays.asList(Tags.JAVA, Tags.SPRING, Tags.REST));
    }

    @Override
    public void buildPO() {
        task.setPO(null);
    }

    @Override
    public void buildDeadline() {
        try {
            task.setDeadline(new SimpleDateFormat("yyyy-MM-dd").parse("2024-12-31"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Task getTask() {
        return task;
    }
}
