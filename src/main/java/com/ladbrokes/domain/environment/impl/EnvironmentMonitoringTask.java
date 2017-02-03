package com.ladbrokes.domain.environment.impl;

import com.ladbrokes.domain.check.Check;
import com.ladbrokes.domain.check.CheckException;
import com.ladbrokes.domain.environment.*;
import com.ladbrokes.domain.release.BuildDetails;
import com.ladbrokes.domain.release.evt.BuildCheckFailed;
import com.ladbrokes.domain.release.evt.BuildDetected;
import com.ladbrokes.domain.release.evt.BuildNotDetected;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;

/**
 * Environment monitoring task.
 */
@Component
public class EnvironmentMonitoringTask implements ApplicationEventPublisherAware {

    private EnvironmentService environmentService;

    private final static Log LOG = LogFactory.getLog(EnvironmentMonitoringTask.class);

    private ApplicationEventPublisher eventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }

    @Scheduled(fixedDelay = 60000)
    public void monitorEnvironments(){
        Collection<EnvironmentDetails> environments = environmentService.getEnvironments();
        for(EnvironmentDetails environment:environments){
            LOG.info(String.format("checking environment %s", environment.getName()));

            Check check = new Check();
            check.setCheckTime(new Date());
            check.setEnvironmentId(new ObjectId(environment.getId()));
            EnvironmentCheck checkPerformer = findCheckPerformer(environment);
            try {
                BuildDetails buildDetails = checkPerformer.checkDeployedBuild(environment);
                if(buildDetails!=null){
                    LOG.info(String.format("found build %s on %s", buildDetails, environment.getName()));
                    BuildDetected buildDetected = new BuildDetected(environment, buildDetails);
                    eventPublisher.publishEvent(buildDetected);
                } else {
                    LOG.warn(String.format("found no build deployed on %s",environment));
                    eventPublisher.publishEvent(new BuildNotDetected(environment));
                }
            } catch (CheckException e) {
                eventPublisher.publishEvent(new BuildCheckFailed(environment,e));
            }

            //TODO cleanup of checks should be performed ...
        }
    }

    private EnvironmentCheck findCheckPerformer(EnvironmentDetails environment) {
        //Unless there are other ways of checking versions it will always return same implementation...
        return new VersionTxtEnvironmentCheck();
    }


    @Autowired
    public void setEnvironmentService(EnvironmentService environmentService) {
        this.environmentService = environmentService;
    }

}
