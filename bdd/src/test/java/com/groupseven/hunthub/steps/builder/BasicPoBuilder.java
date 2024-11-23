package com.groupseven.hunthub.steps.builder;

import com.groupseven.hunthub.domain.models.PO;
import com.groupseven.hunthub.steps.interfaces.PoBuilder;
import java.util.UUID;

public class BasicPoBuilder implements PoBuilder {

    private final PO po;

    public BasicPoBuilder() {
        this.po = new PO();
    }

    public void buildName() {
        po.setName("John Doe");
    }

    public void buildEmail() {
        po.setEmail("johndoe@example.com");
    }

    public void buildPassword() {
        po.setPassword("po123");
    }

    public void buildProfilePicture() {
        po.setProfilePicture("https://example.com/johndoe.jpg");
    }

    public void buildCpf() {
        po.setCpf("12345678901");
    }

    public void buildTasks() {
        po.setTasks(null);
    }

    public void buildTotalRating() {
        po.setTotalRating(0);
    }

    public void buildRatingCount() {
        po.setRatingCount(0);
    }

    public void buildBio() {
        po.setBio("Experienced developer.");
    }

    public void buildLevels() {
        po.setLevels(0);
    }

    public void buildPoId() {
        po.setId(UUID.randomUUID());
    }

    @Override
    public void buildRating() {
        po.setRating(5);
    }

    public PO getPo() {
        return po;
    }

}
