package com.ladbrokes.web.check;

import com.ladbrokes.domain.check.CheckDetails;
import com.ladbrokes.domain.check.CheckService;
import com.ladbrokes.web.jsonapi.SingleJsonApiObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Check resource.
 */
@RestController
@RequestMapping("/api/checks")
public class CheckResource {

    private CheckService checkService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getDashboardStatus(@RequestParam(name = "environment") String environment,
                                    @RequestParam(name="type", required = false) String type){

        if(type!=null && type.equalsIgnoreCase("last_deploy")){
            CheckDetails lastCheckDetails = checkService.findLastUpdate(environment);
            if(lastCheckDetails!=null){
                CheckJson checkJson = new CheckJson(lastCheckDetails);
                return new ResponseEntity(new SingleJsonApiObject<CheckJson>(checkJson), HttpStatus.OK);
            } else {
                return new ResponseEntity(new SingleJsonApiObject<CheckJson>(), HttpStatus.OK);
            }
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Autowired
    public void setCheckService(CheckService checkService) {
        this.checkService = checkService;
    }
}
