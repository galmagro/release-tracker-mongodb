package com.ladbrokes.domain.release;

import java.util.List;

/**
 * Set of release details.
 */
public class BuildDetails {

    private String id;

    private String buildNumber;

    private String description;

    private List<String> issueIds;

    private String releaseId;

    public BuildDetails(String buildNumber, String description) {
        this.buildNumber = buildNumber;
        this.description = description;
    }

    public void setReleaseId(String releaseId) {
        this.releaseId = releaseId;
    }

    public String getBuildNumber() {
        return buildNumber;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getIssueIds() {
        return issueIds;
    }

    public void setIssueIds(List<String> issueIds) {
        this.issueIds = issueIds;
    }

    public String getReleaseId() {
        return releaseId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BuildDetails{" +
                "buildNumber='" + buildNumber + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
