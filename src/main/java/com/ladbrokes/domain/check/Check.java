package com.ladbrokes.domain.check;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * Information about a specific environment check.
 */
public class Check {

    /**
     * Internal identifier.
     */
    @Id
    private ObjectId id;

    /**
     * Build number detected (if any).
     */
    private ObjectId buildId;

    /**
     * Check timestamp.
     */
    private Date checkTime;

    /**
     *  Target environment of the deployment.
     */
    private ObjectId environmentId;

    /**
     * If there was an error while checking.
     */
    private boolean error;

    /**
     * Flag indicating if the build was not registered at the time of the check.
     */
    private boolean unknown;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public ObjectId getEnvironmentId() {
        return environmentId;
    }

    public void setEnvironmentId(ObjectId environmentId) {
        this.environmentId = environmentId;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public ObjectId getBuildId() {
        return buildId;
    }

    public void setBuildId(ObjectId buildId) {
        this.buildId = buildId;
    }

    public boolean isUnknown() {
        return unknown;
    }

    public void setUnknown(boolean unknown) {
        this.unknown = unknown;
    }
}
