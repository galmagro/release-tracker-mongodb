package com.ladbrokes.domain.release.evt;

import com.ladbrokes.domain.environment.EnvironmentDetails;
import org.springframework.context.ApplicationEvent;

/**
 * Event raised when no build is detected on an environment.
 */
public class BuildNotDetected extends ApplicationEvent {

    public BuildNotDetected(EnvironmentDetails environment) {
        super(environment);
    }

    public EnvironmentDetails getTargetEnvironment(){
        return (EnvironmentDetails) getSource();
    }
}
