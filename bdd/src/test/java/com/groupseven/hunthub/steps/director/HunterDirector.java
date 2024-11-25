package com.groupseven.hunthub.steps.director;

import com.groupseven.hunthub.domain.models.Hunter;
import com.groupseven.hunthub.steps.builder.BasicHunterBuilder;

import java.util.Map;

public class HunterDirector {

    private final BasicHunterBuilder hunterBuilder;

    public HunterDirector(BasicHunterBuilder hunterBuilder) {
        this.hunterBuilder = hunterBuilder;
    }



    public Hunter getSpecificHunter(String name) {
        return hunterBuilder.getSpecificHunter(name);
    }


    public Map<String, Hunter> getAllHunters() {
        return hunterBuilder.getAllPredefinedHunters();
    }
}
