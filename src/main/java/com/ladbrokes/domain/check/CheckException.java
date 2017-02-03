package com.ladbrokes.domain.check;

import com.ladbrokes.domain.environment.EnvironmentDetails;

/**
 * Environment check exception.
 */
public class CheckException extends Exception {

    private EnvironmentDetails environment;

    public CheckException(Throwable cause, EnvironmentDetails environment) {
        super(cause);
        this.environment = environment;
    }

    public EnvironmentDetails getEnvironment() {
        return environment;
    }
}
