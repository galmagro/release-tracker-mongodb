package com.ladbrokes.domain.release;

/**
 * Build exception.
 */
public class BuildException extends IllegalArgumentException {

    private String field;

    public BuildException(String field, String message) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
