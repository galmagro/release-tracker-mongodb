package com.ladbrokes.domain.environment;

import java.util.Collection;

/**
 * Domain service for environments.
 */
public interface EnvironmentService {

    EnvironmentDetails registerNewEnvironment(String name, String description, String url);

    Collection<EnvironmentDetails> getEnvironments();

    void updateEnvironment(EnvironmentDetails newDetails);

    EnvironmentDetails findEnvironment(String id);
}
