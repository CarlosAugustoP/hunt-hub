package com.groupseven.hunthub.steps.director;

import com.groupseven.hunthub.domain.models.Task;
import com.groupseven.hunthub.steps.interfaces.TaskBuilder;

import java.util.Map;

public class TaskDirector {
    private final TaskBuilder taskBuilder;

    public TaskDirector(TaskBuilder taskBuilder) {
        this.taskBuilder = taskBuilder;
    }

    public void constructTask() {
        taskBuilder.buildDescription();
        taskBuilder.buildTitle();
        taskBuilder.buildTaskId();
        taskBuilder.buildStatus();
        taskBuilder.buildReward();
        taskBuilder.buildNumberOfHuntersRequired();
        taskBuilder.buildRatingRequired();
        taskBuilder.buildTags();
        taskBuilder.buildPO();
        taskBuilder.buildDeadline();
    }

    public Task getTask() {
        return this.taskBuilder.getTask();
    }

}
