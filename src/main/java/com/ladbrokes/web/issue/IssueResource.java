package com.ladbrokes.web.issue;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ladbrokes.domain.issue.IssueDetails;
import com.ladbrokes.domain.issue.IssueService;
import com.ladbrokes.web.jsonapi.SingleJsonApiObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

/**
 * Issue resource.
 */
@RestController
@RequestMapping("/api/issues")
public class IssueResource {

    private IssueService issueService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/vnd.api+json")
    public ResponseEntity getIssue(@PathVariable("id") String id){
        IssueDetails issueDetails = issueService.findIssue(id);
        IssueJson issueJson = new IssueJson(issueDetails);
        return new ResponseEntity(new SingleJsonApiObject<IssueJson>(issueJson), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/vnd.api+json")
    public ResponseEntity lookupIssue(@RequestParam(value="filter[number]", required = false) String lookupKey){
        if (lookupKey !=null) {
            IssueDetails issueDetails = issueService.lookupByNumber(lookupKey);
            if(issueDetails!=null){
                IssueJson issueJson = new IssueJson(issueDetails);
                return new ResponseEntity(new SingleJsonApiObject<IssueJson>(issueJson),HttpStatus.OK);
            }
            return new ResponseEntity(new SingleJsonApiObject<IssueJson>(),HttpStatus.OK);

        } else {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
    }

    private Map<String,String> getFilterMap(@RequestParam(value = "filter", required = false) String filter)  {
        try {
            return MAPPER.readValue(filter,new TypeReference<Map<String,String>>(){});
        } catch (IOException e) {
            return null;
        }
    }

    @Autowired
    public void setIssueService(IssueService issueService) {
        this.issueService = issueService;
    }
}
