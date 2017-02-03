package com.ladbrokes.web.release;

import com.ladbrokes.domain.release.ReleaseDetails;
import com.ladbrokes.web.jsonapi.JsonApiArray;
import com.ladbrokes.web.jsonapi.ResourceIdentifier;
import com.ladbrokes.web.jsonapi.ResourceObject;

/**
 * Json api wrapper for release data.
 */
public class ReleaseJson extends ResourceObject {

    private ReleaseRelationShips relationships;

    public ReleaseJson() {
    }

    public ReleaseJson(ReleaseDetails releaseDetails){
        this.setType("releases");
        this.setId(String.valueOf(releaseDetails.getId()));
        setAttribute("name",releaseDetails.getName());
        setAttribute("description",releaseDetails.getDescription());

        if (!releaseDetails.getBuildIds().isEmpty()) {
            relationships = new ReleaseRelationShips();
            for(String buildId:releaseDetails.getBuildIds()){
                relationships.addBuild(new ResourceIdentifier("builds",buildId));
            }
        }
    }



    public ReleaseRelationShips getRelationships() {
        return relationships;
    }

    public void setRelationships(ReleaseRelationShips relationships) {
        this.relationships = relationships;
    }

    public class ReleaseRelationShips {

        private JsonApiArray<ResourceIdentifier> builds;

        public ReleaseRelationShips() {
            this.builds = new JsonApiArray<>();
        }

        public JsonApiArray<ResourceIdentifier> getBuilds() {
            return builds;
        }

        public void setBuilds(JsonApiArray<ResourceIdentifier> builds) {
            this.builds = builds;
        }

        public void addBuild(ResourceIdentifier buildIdentifier){
            this.builds.addObject(buildIdentifier);
        }
    }
}
