package com.groupseven.hunthub.domain.models;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "achievements")
public class Achievement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String icon;

    public Achievement(String name, String description, String icon) {
        this.name = name;
        this.description = description;
        this.icon = icon;
    }

    public Achievement() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

}


