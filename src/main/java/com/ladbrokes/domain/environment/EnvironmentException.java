package com.ladbrokes.domain.environment;

/**
 * Environment exception.
 */
public class EnvironmentException extends IllegalArgumentException {

    private String field;

    public EnvironmentException(String field,String message) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
