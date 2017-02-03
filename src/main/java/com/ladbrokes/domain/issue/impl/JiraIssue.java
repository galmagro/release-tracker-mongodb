package com.ladbrokes.domain.issue.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

/**
 * Json deserialization object.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraIssue {

    private String key;

    private Map<String,Object> fields;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Map<String,Object> getFields() {
        return fields;
    }

    public void setFields(Map<String,Object> fields) {
        this.fields = fields;
    }

    public String getSummary(){
        if (fields!=null && fields.containsKey("summary")) {
            return fields.get("summary").toString();
        } else {
            return null;
        }
    }

    public String getDescription(){
        if (fields!=null && fields.containsKey("description")) {
            return fields.get("description").toString();
        } else {
            return null;
        }
    }
}
