package com.ladbrokes.web.jsonapi;

import com.ladbrokes.domain.environment.EnvironmentException;
import com.ladbrokes.domain.release.BuildException;
import com.ladbrokes.domain.release.ReleaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Transforms exceptions into JSON API error messages.
 */
@ControllerAdvice
public class JsonApiErrorHandler {

    @ExceptionHandler
    public ResponseEntity handleEnvironmentException(EnvironmentException ex){
        JsonApiErrors errors = new JsonApiErrors();
        JsonApiError error = new JsonApiError(HttpStatus.UNPROCESSABLE_ENTITY.value(),ex.getField());
        error.setDetail(ex.getMessage());
        errors.addError(error);
        return new ResponseEntity(errors, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    public ResponseEntity handleReleaseException(ReleaseException ex){
        JsonApiErrors errors = new JsonApiErrors();
        JsonApiError error = new JsonApiError(HttpStatus.UNPROCESSABLE_ENTITY.value(),ex.getField());
        error.setDetail(ex.getMessage());
        errors.addError(error);
        return new ResponseEntity(errors, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    public ResponseEntity handleBuildException(BuildException ex){
        JsonApiErrors errors = new JsonApiErrors();
        JsonApiError error = new JsonApiError(HttpStatus.UNPROCESSABLE_ENTITY.value(),ex.getField());
        error.setDetail(ex.getMessage());
        errors.addError(error);
        return new ResponseEntity(errors, HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
