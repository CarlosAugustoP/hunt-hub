package com.groupseven.hunthub.steps.interfaces;

import com.groupseven.hunthub.domain.models.Hunter;

public interface HunterBuilder {
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
    void buildHunterId();
    void buildCertifications();
    void buildLinks();
    void buildAchievements();
    void buildProjects();
    Hunter getHunter();
}
