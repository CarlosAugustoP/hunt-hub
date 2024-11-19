package com.groupseven.hunthub.domain.models;

public class RatingCheck {
    private final UserId hunterId;
    private final TaskId taskId;
    private final UserId poId;

    public RatingCheck(UserId hunterId, TaskId taskId, UserId poId) {
        this.hunterId = hunterId;
        this.taskId = taskId;
        this.poId = poId;
    }

    public UserId getHunterId() {
        return hunterId;
    }

    public TaskId getTaskId() {
        return taskId;
    }

    public UserId getPoId() {
        return poId;
    }
}
