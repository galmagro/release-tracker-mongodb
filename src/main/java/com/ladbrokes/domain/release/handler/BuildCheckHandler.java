package com.ladbrokes.domain.release.handler;

import com.ladbrokes.domain.check.CheckService;
import com.ladbrokes.domain.environment.EnvironmentDetails;
import com.ladbrokes.domain.release.BuildDetails;
import com.ladbrokes.domain.release.ReleaseService;
import com.ladbrokes.domain.release.evt.BuildCheckFailed;
import com.ladbrokes.domain.release.evt.BuildDetected;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Handler of build check events.
 */
@Service
public class BuildCheckHandler {

    private ReleaseService releaseService;

    private CheckService checkService;


    @EventListener
    @Async
    public void handleBuildDetected(BuildDetected buildDetectedEvt){
        BuildDetails detectedBuild = buildDetectedEvt.getDetectedBuild();
        BuildDetails registeredBuild = releaseService.findBuildByNumber(detectedBuild.getBuildNumber());
        EnvironmentDetails targetEnvironment = buildDetectedEvt.getTargetEnvironment();
        if(registeredBuild!=null){
            checkService.registerKnownDeployment(targetEnvironment, registeredBuild, buildDetectedEvt.getCheckTime());
        } else {
            BuildDetails unallocatedBuild = releaseService.registerUnallocatedBuild(detectedBuild);
            checkService.registerUnknownDeployment(targetEnvironment, unallocatedBuild, buildDetectedEvt.getCheckTime());
        }
    }

    @EventListener
    @Async
    public void handleBuildCheckFailed(BuildCheckFailed buildCheckFailed){
        EnvironmentDetails environment = buildCheckFailed.getTargetEnvironment();
        checkService.registerCheckError(environment,buildCheckFailed.getCheckTime());
    }


    @Autowired
    public void setReleaseService(ReleaseService releaseService) {
        this.releaseService = releaseService;
    }

    @Autowired
    public void setCheckService(CheckService checkService) {
        this.checkService = checkService;
    }
}
