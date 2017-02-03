package com.ladbrokes.domain.release.handler;

import com.ladbrokes.domain.check.CheckService;
import com.ladbrokes.domain.environment.EnvironmentDetails;
import com.ladbrokes.domain.release.evt.BuildCheckFailed;
import com.ladbrokes.domain.release.evt.BuildDetected;
import com.ladbrokes.domain.release.evt.BuildNotDetected;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Component that performs check history cleanup.
 */
@Service
public class CheckHistoryCleaner {

    private CheckService checkService;

    private final static Log LOG = LogFactory.getLog(CheckHistoryCleaner.class);

    @EventListener
    @Async
    public void handleBuildDetected(BuildDetected buildDetectedEvt){
        deleteHistory(buildDetectedEvt.getTargetEnvironment());
    }

    private void deleteHistory(EnvironmentDetails env) {
        LOG.debug(String.format("cleaning history for environment %s",env));
        checkService.deleteEnvironmentHistory(env.getId());
    }

    @EventListener
    @Async
    public void handleBuildNotDetected(BuildNotDetected buildNotDetectedEvt){
        deleteHistory(buildNotDetectedEvt.getTargetEnvironment());
    }

    @EventListener
    @Async
    public void handleBuildCheckFailed(BuildCheckFailed buildCheckFailedEvt){
        deleteHistory(buildCheckFailedEvt.getTargetEnvironment());
    }

    @Autowired
    public void setCheckService(CheckService checkService) {
        this.checkService = checkService;
    }
}
