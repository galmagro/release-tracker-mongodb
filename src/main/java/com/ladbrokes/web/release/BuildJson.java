package com.ladbrokes.web.release;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ladbrokes.domain.release.BuildDetails;
import com.ladbrokes.web.jsonapi.JsonApiArray;
import com.ladbrokes.web.jsonapi.ResourceIdentifier;
import com.ladbrokes.web.jsonapi.ResourceObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Json api object for build entities.
 */
public class BuildJson extends ResourceObject {

    private BuildRelationships relationships;

    public BuildJson() {
    }

    public BuildJson(BuildDetails buildDetails) {
        setType("builds");
        setId(String.valueOf(buildDetails.getId()));
        setAttribute("number",buildDetails.getBuildNumber());
        setAttribute("description",buildDetails.getDescription());

        if(buildDetails.getReleaseId()!=null){
            ensureRelationships();
            relationships.setReleaseId(buildDetails.getReleaseId());
        }

        if(buildDetails.getIssueIds() != null){
            ensureRelationships();
            for(String issueId:buildDetails.getIssueIds()){
                relationships.addIssue(issueId);
            }
        }
    }

    private void ensureRelationships() {
        if(relationships == null){
            relationships = new BuildRelationships();
        }
    }

    public BuildRelationships getRelationships() {
        return relationships;
    }

    public void setRelationships(BuildRelationships relationships) {
        this.relationships = relationships;
    }

    @JsonIgnore
    public String getReleaseId() {
        return relationships.getReleaseId();
    }

    @JsonIgnore
    public List<String> getIssueIds(){
        if(relationships.getIssues()!=null){
            List<String> issueIds = new LinkedList<>();
            for(ResourceIdentifier issueIdentifier:relationships.getIssues().getData()){
                issueIds.add(issueIdentifier.getId());
            }
            return issueIds;
        }
        return null;
    }

    public class BuildRelationships {

        private JsonApiArray<ResourceIdentifier> issues = new JsonApiArray<>();

        private SingleDataContainer release = new SingleDataContainer();

        public JsonApiArray<ResourceIdentifier> getIssues() {
            return issues;
        }

        public void setIssues(JsonApiArray<ResourceIdentifier> issues) {
            this.issues = issues;
        }

        public SingleDataContainer getRelease() {
            return release;
        }

        public void setRelease(SingleDataContainer release) {
            this.release = release;
        }

        public void setReleaseId(String releaseId){
            ResourceIdentifier releaseIdentifier = new ResourceIdentifier("releases", releaseId);
            this.release = new SingleDataContainer(releaseIdentifier);
        }

        @JsonIgnore
        public String getReleaseId(){
            if (getRelease()!=null && getRelease().getData()!=null) {
                return getRelease().getData().getId();
            }
            return null;
        }

        public void addIssue(String issueId) {
            ResourceIdentifier identifier = new ResourceIdentifier("issues",issueId);
            issues.addObject(identifier);
        }

    }


}
