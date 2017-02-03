package com.ladbrokes.web.release;

import com.ladbrokes.domain.release.ReleaseDetails;
import com.ladbrokes.domain.release.ReleaseService;
import com.ladbrokes.web.jsonapi.JsonApiArray;
import com.ladbrokes.web.jsonapi.SingleJsonApiObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Release REST resource.
 */
@RestController
@RequestMapping("/api/releases")
public class ReleaseResource {

    private ReleaseService releaseService;

    @RequestMapping(method = RequestMethod.POST, produces = "application/vnd.api+json")
    public ResponseEntity createRelease(HttpEntity<SingleJsonApiObject<ReleaseJson>> jsonEntity){
        ReleaseJson releaseJson = jsonEntity.getBody().getData();
        ReleaseDetails newReleaseDetails = releaseService.registerRelease(
                releaseJson.getAttribute("name"),
                releaseJson.getAttribute("description"));
        ReleaseJson responseJson = new ReleaseJson(newReleaseDetails);
        return new ResponseEntity(new SingleJsonApiObject<ReleaseJson>(responseJson), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/vnd.api+json")
    public ResponseEntity getReleases(){
        JsonApiArray<ReleaseJson> releasesArray = new JsonApiArray<>();
        Collection<ReleaseDetails> releases = releaseService.findReleases();
        for(ReleaseDetails release:releases){
            releasesArray.addObject(new ReleaseJson(release));
        }
        return new ResponseEntity(releasesArray, HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET, produces = "application/vnd.api+json")
    public ResponseEntity findRelease(@PathVariable("id") String id){
        ReleaseDetails releaseDetails = releaseService.findRelease(id);
        ReleaseJson responseJson = new ReleaseJson(releaseDetails);
        return new ResponseEntity(new SingleJsonApiObject<ReleaseJson>(responseJson), HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.PATCH, produces = "application/vnd.api+json")
    public ResponseEntity updateRelease(@PathVariable("id") String id, HttpEntity<SingleJsonApiObject<ReleaseJson>> jsonEntity){
        ReleaseJson releaseJson = jsonEntity.getBody().getData();
        ReleaseDetails updatedReleaseDetails = new ReleaseDetails();
        updatedReleaseDetails.setId(id);
        updatedReleaseDetails.setName(releaseJson.getAttribute("name"));
        updatedReleaseDetails.setDescription(releaseJson.getAttribute("description"));
        releaseService.updateRelease(updatedReleaseDetails);
        return new ResponseEntity(new SingleJsonApiObject<ReleaseJson>(releaseJson), HttpStatus.OK);
    }


    @Autowired
    public void setReleaseService(ReleaseService releaseService) {
        this.releaseService = releaseService;
    }
}
