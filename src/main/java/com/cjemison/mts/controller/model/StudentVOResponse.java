package com.cjemison.mts.controller.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by cjemison on 7/23/16.
 */
public class StudentVOResponse {
    private final String id;
    private final String createdDate;

    @JsonCreator
    public StudentVOResponse(@JsonProperty("id")
                             final String id,
                             @JsonProperty("createdDate")
                             final String createdDate) {
        this.id = id;
        this.createdDate = createdDate;
    }

    public String getId() {
        return id;
    }

    public String getCreatedDate() {
        return createdDate;
    }

}
