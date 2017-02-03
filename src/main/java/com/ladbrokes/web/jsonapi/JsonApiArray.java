package com.ladbrokes.web.jsonapi;

import java.util.LinkedList;
import java.util.List;

/**
 * JSON api dto for arrays of objects.
 */
public class JsonApiArray<T extends ResourceIdentifier> {

    private List<T> data = new LinkedList<>();

    public List<T> getData() {
        return data;
    }

    public void addObject(T apiObject){
        data.add(apiObject);
    }
}
