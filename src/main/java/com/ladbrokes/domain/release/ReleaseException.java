package com.ladbrokes.domain.release;

/**
 * Release exception.
 */
public class ReleaseException extends IllegalArgumentException {

    private String field;

    public ReleaseException(String field, String message) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
