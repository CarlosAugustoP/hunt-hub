package com.groupseven.hunthub.steps.director;

import com.groupseven.hunthub.domain.models.PO;
import com.groupseven.hunthub.steps.interfaces.PoBuilder;

public class PODirector {
    private final PoBuilder poBuilder;

    public PODirector(PoBuilder poBuilder) {
        this.poBuilder = poBuilder;
    }

    public void constructPO() {
        poBuilder.buildName();
        poBuilder.buildEmail();
        poBuilder.buildPassword();
        poBuilder.buildProfilePicture();
        poBuilder.buildCpf();
        poBuilder.buildTasks();
        poBuilder.buildTotalRating();
        poBuilder.buildRatingCount();
        poBuilder.buildBio();
        poBuilder.buildLevels();
        poBuilder.buildPoId();
    }

    public PO getPO() {
        return this.poBuilder.getPo();
    }

}
