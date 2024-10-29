package com.groupseven.hunthub.persistence.jpa.mapper;

import com.groupseven.hunthub.persistence.jpa.models.TagsJPA;
import com.groupseven.hunthub.domain.models.Tags;

public class TagsMapper {

    public static TagsJPA toEntity(Tags tag) {
        return TagsJPA.valueOf(tag.name());
    }

    public static Tags toDomain(TagsJPA tagJpa) {
        return Tags.valueOf(tagJpa.name());
    }
}