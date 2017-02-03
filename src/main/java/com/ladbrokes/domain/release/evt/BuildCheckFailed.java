package com.ladbrokes.domain.release.evt;

import com.ladbrokes.domain.check.CheckException;
import com.ladbrokes.domain.environment.EnvironmentDetails;
import org.springframework.context.ApplicationEvent;

import java.util.Date;

/**
 * Event raised when a check on an environment has failed.
 */
public class BuildCheckFailed extends ApplicationEvent {

    private CheckException checkException;

    private Date checkTime;

    public BuildCheckFailed(EnvironmentDetails environment, CheckException exception) {
        super(environment);
        this.checkException = exception;
        this.checkTime = new Date();
    }

    public EnvironmentDetails getTargetEnvironment(){
        return (EnvironmentDetails) getSource();
    }

    public CheckException getCheckException() {
        return checkException;
    }

    public Date getCheckTime() {
        return checkTime;
    }
}
