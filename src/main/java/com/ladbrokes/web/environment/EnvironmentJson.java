package com.ladbrokes.web.environment;

import com.ladbrokes.domain.environment.EnvironmentDetails;
import com.ladbrokes.web.jsonapi.ResourceObject;

/**
 * JSON-api DTO for environment.
 */
public class EnvironmentJson extends ResourceObject {

    public EnvironmentJson() {
    }

    public EnvironmentJson(EnvironmentDetails envDetails){
        super();
        setId(String.valueOf(envDetails.getId()));
        setType("environments");
        this.setAttribute("name",envDetails.getName());
        this.setAttribute("description",envDetails.getDescription());
        this.setAttribute("url",envDetails.getUrl());
    }



}
