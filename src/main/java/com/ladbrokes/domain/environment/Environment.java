package com.ladbrokes.domain.environment;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

/**
 * Check environment.
 */
public class Environment {

    /**
     * Internal identifier.
     */
    @Id
    private ObjectId id;

    /**
     * Environment name.
     */
    private String name;

    /**
     * Environment description.
     */

    private String description;

    /**
     * Environment url.
     */
    private String url;


    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }





}
