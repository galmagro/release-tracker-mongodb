package com.ladbrokes.domain.release.evt;

import com.ladbrokes.domain.environment.EnvironmentDetails;
import com.ladbrokes.domain.release.BuildDetails;
import org.springframework.context.ApplicationEvent;

import java.util.Date;

/**
 * Build found event.
 */
public class BuildDetected extends ApplicationEvent {

    private BuildDetails build;

    private Date checkTime;

    public BuildDetected(EnvironmentDetails environment, BuildDetails build) {
        super(environment);
        this.build = build;
        this.checkTime = new Date();
    }

    public BuildDetails getDetectedBuild() {
        return build;
    }

    public EnvironmentDetails getTargetEnvironment(){
        return (EnvironmentDetails) getSource();
    }

    public Date getCheckTime() {
        return checkTime;
    }
}
