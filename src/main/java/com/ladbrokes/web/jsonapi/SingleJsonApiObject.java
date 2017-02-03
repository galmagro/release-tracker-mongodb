package com.ladbrokes.web.jsonapi;

/**
 * Wrapper for JSON api responses containing a single object.
 */
public class SingleJsonApiObject<T extends ResourceObject> {

    private T data;

    public SingleJsonApiObject() {
    }

    public SingleJsonApiObject(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
