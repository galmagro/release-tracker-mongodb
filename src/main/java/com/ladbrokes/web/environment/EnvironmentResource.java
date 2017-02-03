package com.ladbrokes.web.environment;

import com.ladbrokes.domain.environment.EnvironmentDetails;
import com.ladbrokes.domain.environment.EnvironmentService;
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
 * REST resource for environments.
 */
@RestController
@RequestMapping("/api/environments")
public class EnvironmentResource {

    private EnvironmentService environmentService;

    @RequestMapping(method = RequestMethod.POST, produces = "application/vnd.api+json")
    public ResponseEntity createEnvironment(
            HttpEntity<SingleJsonApiObject<EnvironmentJson>> envJson){

        EnvironmentJson jsonBody = envJson.getBody().getData();
        EnvironmentDetails newEnvDetails = environmentService.registerNewEnvironment(
                jsonBody.getAttribute("name"),
                jsonBody.getAttribute("description"),
                jsonBody.getAttribute("url"));
        jsonBody.setId(String.valueOf(newEnvDetails.getId()));
        return new ResponseEntity(envJson.getBody(), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PATCH,
            produces = "application/vnd.api+json")
    public ResponseEntity updateEnvironment(
            @PathVariable("id") String id,
            HttpEntity<SingleJsonApiObject<EnvironmentJson>> envJson){

        EnvironmentJson jsonBody = envJson.getBody().getData();
        EnvironmentDetails newDetails = new EnvironmentDetails();
        newDetails.setId(id);
        newDetails.setName(jsonBody.getAttribute("name"));
        newDetails.setDescription(jsonBody.getAttribute("description"));
        newDetails.setUrl(jsonBody.getAttribute("url"));
        environmentService.updateEnvironment(newDetails);
        return new ResponseEntity(envJson.getBody(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = "application/vnd.api+json")
    public ResponseEntity getEnvironment(@PathVariable("id") String id){
        EnvironmentDetails environment = environmentService.findEnvironment(id);
        SingleJsonApiObject<EnvironmentJson> jsonApiObject = new SingleJsonApiObject<>();
        if (environment!=null) {
            jsonApiObject.setData(new EnvironmentJson(environment));
        }

        return new ResponseEntity(jsonApiObject,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/vnd.api+json")
    public ResponseEntity getEnvironments(){
        Collection<EnvironmentDetails> environments = environmentService.getEnvironments();
        JsonApiArray<EnvironmentJson> jsonApiArray = new JsonApiArray<>();
        for(EnvironmentDetails environment:environments){
            jsonApiArray.addObject(new EnvironmentJson(environment));
        }
        return new ResponseEntity(jsonApiArray,HttpStatus.OK);
    }

    @Autowired
    public void setEnvironmentService(EnvironmentService environmentService) {
        this.environmentService = environmentService;
    }
}
