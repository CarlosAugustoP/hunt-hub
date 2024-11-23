package com.groupseven.hunthub.domain.interfaces;

import com.groupseven.hunthub.domain.models.Task;

public interface Observer {
    void update(String message, String theme, Task task);
}