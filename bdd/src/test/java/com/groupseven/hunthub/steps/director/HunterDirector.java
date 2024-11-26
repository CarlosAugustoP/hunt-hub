package com.groupseven.hunthub.steps.director;

import com.groupseven.hunthub.domain.models.Hunter;
import com.groupseven.hunthub.steps.interfaces.HunterBuilder;

public class HunterDirector {

    private final HunterBuilder hunterBuilder;

    public HunterDirector(HunterBuilder hunterBuilder) {
        this.hunterBuilder = hunterBuilder;
    }

    public void constructHunter() {
        hunterBuilder.buildName();
        hunterBuilder.buildEmail();
        hunterBuilder.buildPassword();
        hunterBuilder.buildProfilePicture();
        hunterBuilder.buildCpf();
        hunterBuilder.buildTasks();
        hunterBuilder.buildTotalRating();
        hunterBuilder.buildRatingCount();
        hunterBuilder.buildBio();
        hunterBuilder.buildLevels();
        hunterBuilder.buildHunterId();
        hunterBuilder.buildCertifications();
        hunterBuilder.buildLinks();
        hunterBuilder.buildAchievements();
        hunterBuilder.buildProjects();
    }

    public Hunter getHunter() {
        return this.hunterBuilder.getHunter();
    }
}