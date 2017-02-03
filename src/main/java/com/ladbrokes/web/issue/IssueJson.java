package com.ladbrokes.web.issue;

import com.ladbrokes.domain.issue.IssueDetails;
import com.ladbrokes.web.jsonapi.ResourceObject;

/**
 * Issue resource object.
 */
public class IssueJson extends ResourceObject {

    public IssueJson() {
    }

    public IssueJson(IssueDetails issueDetails){
        setType("issues");
        setId(String.valueOf(issueDetails.getId()));
        setAttribute("number",issueDetails.getNumber());
        setAttribute("title",issueDetails.getTitle());
        setAttribute("description",issueDetails.getDescription());
    }

}
