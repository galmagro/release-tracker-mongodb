package com.ladbrokes.domain.issue;

/**
 * Issue service.
 */
public interface IssueService {

    IssueDetails findIssue(String id);

    IssueDetails lookupByNumber(String number);

}
