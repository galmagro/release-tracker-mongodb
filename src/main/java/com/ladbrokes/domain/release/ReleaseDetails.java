package com.ladbrokes.domain.release;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Release details DTO.
 */
public class ReleaseDetails {

    private String id;

    private String name;

    private String description;

    private List<String> buildIds = new LinkedList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<String> getBuildIds() {
        return Collections.unmodifiableList(buildIds);
    }

    public void addBuild(String buildId) {
        this.buildIds.add(buildId);
    }
}
