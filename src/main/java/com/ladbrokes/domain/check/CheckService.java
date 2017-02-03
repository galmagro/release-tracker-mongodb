package com.ladbrokes.domain.check;

import com.ladbrokes.domain.environment.EnvironmentDetails;
import com.ladbrokes.domain.release.BuildDetails;
import org.bson.types.ObjectId;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Check service.
 */
public interface CheckService {

    void registerKnownDeployment(EnvironmentDetails targetEnvironment, BuildDetails registeredBuild, Date checkTime);


    void registerUnknownDeployment(EnvironmentDetails targetEnvironment, BuildDetails unallocatedBuild, Date checkTime);


    void registerCheckError(EnvironmentDetails environment, Date checkTime);

    CheckDetails findLastUpdate(String environmentId);

    @Transactional
    void deleteEnvironmentHistory(String environmentId);
}
