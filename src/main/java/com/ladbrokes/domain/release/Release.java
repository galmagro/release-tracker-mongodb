package com.ladbrokes.domain.release;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * Release entity.
 */
public class Release {

    /**
     * Internal identifier.
     */
    @Id
    private ObjectId id;

    /**
     * Release name.
     */
    private String name;

    /**
     * Release description.
     */
    private String description;

    @DBRef
    private Map<String,Build> builds;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, Build> getBuilds() {
        return builds;
    }

    public Collection<Build> getReleaseBuilds(){
        if (getBuilds()!=null) {
            return Collections.unmodifiableCollection(getBuilds().values());
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    public void setBuilds(Map<String, Build> builds) {
        this.builds = builds;
    }

    public Build addBuild(BuildDetails buildDetails){

        String newBuildNumber = buildDetails.getBuildNumber();
        if(newBuildNumber == null || newBuildNumber.trim().isEmpty()){
            return null;
        }

        if(!builds.containsKey(newBuildNumber)){
            Build build = new Build();
            build.setNumber(newBuildNumber);
            build.setDescription(buildDetails.getDescription());
            build.setRelease(this);
            addBuild(build);
            return build;
        }

        return null;
    }

    public void addBuild(Build build) {
        builds.put(build.getNumber(),build);
    }
}
