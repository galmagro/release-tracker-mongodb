package com.ladbrokes.web.check;

import com.ladbrokes.domain.check.CheckDetails;
import com.ladbrokes.web.jsonapi.ResourceIdentifier;
import com.ladbrokes.web.jsonapi.ResourceObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Json API object for checks.
 */
public class CheckJson extends ResourceObject {

    private final static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    private CheckRelationships relationships;

    public CheckJson() {
    }

    public CheckJson(CheckDetails checkDetails){
        setId(String.valueOf(checkDetails.getId()));
        setType("checks");
        setAttribute("checked",DATE_FORMAT.format(checkDetails.getTime()));
        setAttribute("error",String.valueOf(checkDetails.isError()));
        relationships = new CheckRelationships();
        relationships.setEnvironmentId(checkDetails.getEnvironmentId());
        relationships.setBuildId(checkDetails.getBuildId());
    }

    public static class CheckRelationships {

        private SingleDataContainer build = new SingleDataContainer();

        private SingleDataContainer environment = new SingleDataContainer();

        public SingleDataContainer getBuild() {
            return build;
        }

        public void setBuild(SingleDataContainer build) {
            this.build = build;
        }

        public SingleDataContainer getEnvironment() {
            return environment;
        }

        public void setEnvironment(SingleDataContainer environment) {
            this.environment = environment;
        }

        public void setEnvironmentId(String environmentId){
            if (environmentId!=null) {
                ResourceIdentifier resourceIdentifier = new ResourceIdentifier();
                resourceIdentifier.setType("environments");
                resourceIdentifier.setId(environmentId);
                environment.setData(resourceIdentifier);
            }
        }

        public void setBuildId(String buildId){
            if (buildId!=null) {
                ResourceIdentifier resourceIdentifier = new ResourceIdentifier();
                resourceIdentifier.setType("builds");
                resourceIdentifier.setId(buildId);
                build.setData(resourceIdentifier);
            }
        }
    }

    public CheckRelationships getRelationships() {
        return relationships;
    }

    public void setRelationships(CheckRelationships relationships) {
        this.relationships = relationships;
    }
}
