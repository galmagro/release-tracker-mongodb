package com.ladbrokes.domain.issue.impl;

import com.ladbrokes.domain.issue.Issue;
import com.ladbrokes.domain.issue.IssueDetails;
import com.ladbrokes.domain.issue.IssueRepository;
import com.ladbrokes.domain.issue.IssueService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Issue service implementation.
 */
@Service
public class IssueServiceBean implements IssueService {

    private IssueRepository issueRepository;

    private String jiraUrl;

    private final static RestTemplate REST_TEMPLATE = new RestTemplate();

    private final static Log LOG = LogFactory.getLog(IssueServiceBean.class);

    @Override
    @Transactional(readOnly = true)
    public IssueDetails findIssue(String id) {
        Issue issue = issueRepository.findOne(new ObjectId(id));
        if(issue != null){
            return toIssueDetailsDTO(issue);
        }
        return null;
    }

    @Override
    @Transactional
    public IssueDetails lookupByNumber(String number) {
        LOG.info(String.format("looking up issue , %s", number));
        Issue issue = issueRepository.findByNumberIgnoreCase(number);
        if(issue!=null){
            LOG.info(String.format("issue %s found on local database", number));
            return toIssueDetailsDTO(issue);
        } else {
            //lookup details on JIRA
            LOG.info(String.format("looking up issue %s on JIRA", number));
            JiraIssue jiraIssue = lookupJiraIssue(number);
            if(jiraIssue == null || jiraIssue.getKey() == null){
                return null;
            } else {
                issue = new Issue();
                issue.setTitle(jiraIssue.getSummary());
                issue.setDescription(jiraIssue.getDescription());
                issue.setNumber(jiraIssue.getKey());
                issueRepository.save(issue);

                return toIssueDetailsDTO(issue);
            }
        }
    }

    private JiraIssue lookupJiraIssue(String number) {
        try {
            return REST_TEMPLATE.getForObject(jiraUrl.concat("/jira/rest/api/2/issue/{key}"),
                    JiraIssue.class,
                    number);
        } catch (RestClientException e) {
            LOG.warn("error looking issue " + e.getMessage());
            return null;
        }
    }

    private IssueDetails toIssueDetailsDTO(Issue issue) {
        IssueDetails dto = new IssueDetails();
        dto.setId(issue.getId().toHexString());
        dto.setNumber(issue.getNumber());
        dto.setDescription(issue.getDescription());
        dto.setTitle(issue.getTitle());
        return dto;
    }

    @Autowired
    public void setIssueRepository(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    @Value("${jira.url}")
    public void setJiraUrl(String jiraUrl) {
        this.jiraUrl = jiraUrl;
    }
}
