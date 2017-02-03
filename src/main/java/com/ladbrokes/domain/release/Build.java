package com.ladbrokes.domain.release;

import com.ladbrokes.domain.issue.Issue;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.LinkedList;
import java.util.List;

/**
 * Build entity.
 */
public class Build {

    /**
     * Internal identifier.
     */
    @Id
    private ObjectId id;

    /**
     * Build number.
     */
    private String number;

    /**
     * Build description.
     */
    private String description;

    /**
     * Release where this build is included.
     */
    @DBRef
    private Release release;

    /**
     * Issues.
     */
    @DBRef
    private List<Issue> issues = new LinkedList<>();

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Issue> getIssues() {
        return issues;
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }

    public Release getRelease() {
        return release;
    }

    public void setRelease(Release release) {
        this.release = release;
    }

    public boolean addIssue(Issue newIssue){
        if(newIssue!=null){
            return issues.add(newIssue);
        } else {
            return false;
        }
    }
}
