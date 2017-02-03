package com.ladbrokes.web.release;

import com.ladbrokes.domain.release.BuildDetails;
import com.ladbrokes.domain.release.ReleaseService;
import com.ladbrokes.web.jsonapi.SingleJsonApiObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * JSON-api restful service for build related data.
 */
@RestController
@RequestMapping("/api/builds")
public class BuildResource {

    private ReleaseService releaseService;

    @RequestMapping(method = RequestMethod.POST,
            consumes = "application/vnd.api+json",
            produces = "application/vnd.api+json")
    public ResponseEntity createBuild(HttpEntity<SingleJsonApiObject<BuildJson>> jsonEntity){
        BuildJson buildJson = jsonEntity.getBody().getData();
        String releaseId = buildJson.getReleaseId();
        BuildDetails buildDetails = toBuildDetails(buildJson);
        buildDetails = releaseService.registerReleaseBuild(releaseId,buildDetails);
        BuildJson responseJson = new BuildJson(buildDetails);
        return new ResponseEntity(new SingleJsonApiObject<BuildJson>(responseJson), HttpStatus.CREATED);
    }

    private BuildDetails toBuildDetails(BuildJson buildJson) {
        BuildDetails buildDetails = new BuildDetails(
                buildJson.getAttribute("number"),
                buildJson.getAttribute("description"));
        if (buildJson.getId()!=null) {
            buildDetails.setId(buildJson.getId());
        }
        List<String> issueIds = buildJson.getIssueIds();
        if(issueIds !=null){
            buildDetails.setIssueIds(issueIds);
        }

        if(buildJson.getReleaseId()!=null){
            buildDetails.setReleaseId(buildJson.getReleaseId());
        }

        return buildDetails;
    }


    @RequestMapping(value="/{id}", method = RequestMethod.GET, produces = "application/vnd.api+json")
    public ResponseEntity findBuild(@PathVariable("id") String id){
        BuildDetails buildDetails = releaseService.findBuild(id);
        BuildJson responseJson = new BuildJson(buildDetails);
        return new ResponseEntity(new SingleJsonApiObject<BuildJson>(responseJson), HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.PATCH,
            consumes = "application/vnd.api+json",
            produces = "application/vnd.api+json")
    public ResponseEntity updateBuild(@PathVariable("id") long id, HttpEntity<SingleJsonApiObject<BuildJson>> jsonEntity){
        BuildJson buildJson = jsonEntity.getBody().getData();
        BuildDetails buildDetails = toBuildDetails(buildJson);
        releaseService.updateBuild(buildDetails);
        return new ResponseEntity(new SingleJsonApiObject<BuildJson>(buildJson), HttpStatus.OK);
    }


    @Autowired
    public void setReleaseService(ReleaseService releaseService) {
        this.releaseService = releaseService;
    }

}
