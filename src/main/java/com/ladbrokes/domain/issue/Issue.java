package com.ladbrokes.domain.issue;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import javax.persistence.*;

/**
 * Issue addressed by release.
 */
public class Issue {

    /**
     * Internal identifier.
     */
    @Id
    private ObjectId id;

    /**
     * Issue tracker number, JIRA ticket number.
     */
    private String number;

    private String title;

    private String description;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
