package com.ladbrokes.domain.environment;

import com.ladbrokes.domain.check.CheckException;
import com.ladbrokes.domain.release.BuildDetails;

/**
 * Environment check.
 */
public interface EnvironmentCheck {

    /**
     * Finds what build number is deployed in a specific environment.
     * @param environment environment
     * @return build number deployed.
     */
    BuildDetails checkDeployedBuild(EnvironmentDetails environment) throws CheckException;

}
