package com.groupseven.hunthub.steps.interfaces;

import com.groupseven.hunthub.domain.models.PO;

public interface PoBuilder {
    void buildName();
    void buildEmail();
    void buildPassword();
    void buildProfilePicture();
    void buildCpf();
    void buildTasks();
    void buildTotalRating();
    void buildRatingCount();
    void buildBio();
    void buildLevels();
    void buildPoId();
    void buildRating();
    PO getPo();
}
