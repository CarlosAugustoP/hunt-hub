package com.groupseven.hunthub.steps.interfaces;

import com.groupseven.hunthub.domain.models.Task;

public interface TaskBuilder {
    void buildDescription();
    void buildTitle();
    void buildTaskId();
    void buildStatus();
    void buildReward();
    void buildNumberOfHuntersRequired();
    void buildRatingRequired();
    void buildTags();
    void buildPO();
    void buildDeadline();
    Task getTask();
}
