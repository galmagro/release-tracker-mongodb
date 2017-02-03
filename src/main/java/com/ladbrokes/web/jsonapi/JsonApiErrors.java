package com.ladbrokes.web.jsonapi;

import java.util.LinkedList;
import java.util.List;

/**
 * JSON APi errors wrapper.
 */
public class JsonApiErrors {

    private List<JsonApiError> errors = new LinkedList<>();

    public List<JsonApiError> getErrors() {
        return errors;
    }

    public void setErrors(List<JsonApiError> errors) {
        this.errors = errors;
    }

    public void addError(JsonApiError error){
        this.errors.add(error);
    }
}
