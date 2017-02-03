package com.ladbrokes.domain.environment.impl;

import com.ladbrokes.domain.environment.*;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Environment service implementation.
 */
@Service
public class EnvironmentServiceBean implements EnvironmentService {

    private EnvironmentRepository repository;

    @Override
    @Transactional
    public EnvironmentDetails registerNewEnvironment(String name, String description, String url) {

        if(repository.findByNameIgnoreCase(name)!=null){
            throw new EnvironmentException("name","environment with same name already exists");
        }

        if(!checkUrl(url)){
            throw new EnvironmentException("url","url is not valid");
        }

        Environment environment = new Environment();
        environment.setName(name);
        environment.setDescription(description);
        environment.setUrl(url);

        repository.save(environment);
        return toDetailsDTO(environment);
    }

    private boolean checkUrl(String url) {
        try {
            new URL(url);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    @Override
    public Collection<EnvironmentDetails> getEnvironments() {
        Iterable<Environment> envs = repository.findAll();
        List<EnvironmentDetails> envDetails = new LinkedList<>();
        for(Environment environment:envs){
            envDetails.add(toDetailsDTO(environment));
        }
        return envDetails;
    }

    @Override
    @Transactional
    public void updateEnvironment(EnvironmentDetails newDetails) {
        Environment environment = repository.findOne(new ObjectId(newDetails.getId()));
        if(environment == null){
            throw new EnvironmentException("id","environment not found");
        }

        Environment sameNameEnvironment = repository.findByNameIgnoreCase(newDetails.getName());
        if(sameNameEnvironment!=null && !sameNameEnvironment.getId().toHexString().equals(newDetails.getId())){
            throw new EnvironmentException("name","environment with same name already exists");
        }

        if(!checkUrl(newDetails.getUrl())){
            throw new EnvironmentException("url","url is not valid");
        }

        environment.setName(newDetails.getName());
        environment.setDescription(newDetails.getDescription());
        environment.setUrl(newDetails.getUrl());

        repository.save(environment);

    }

    @Override
    public EnvironmentDetails findEnvironment(String id) {
        Environment environment = repository.findOne(new ObjectId(id));
        if(environment != null){
            return toDetailsDTO(environment);
        } else {
            return null;
        }
    }

    private EnvironmentDetails toDetailsDTO(Environment environment) {
        EnvironmentDetails environmentDetails = new EnvironmentDetails();
        environmentDetails.setId(environment.getId().toHexString());
        environmentDetails.setName(environment.getName());
        environmentDetails.setDescription(environment.getDescription());
        environmentDetails.setUrl(environment.getUrl());
        return environmentDetails;
    }

    @Autowired
    public void setRepository(EnvironmentRepository repository) {
        this.repository = repository;
    }
}
