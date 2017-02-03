package com.ladbrokes.domain.environment.impl;

import com.ladbrokes.domain.environment.EnvironmentCheck;
import com.ladbrokes.domain.environment.EnvironmentDetails;
import com.ladbrokes.domain.release.BuildDetails;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Build deploy check based on the value of the __version.txt file in the environment.
 */
public class VersionTxtEnvironmentCheck implements EnvironmentCheck {

    private final static RestTemplate REST_TEMPLATE = new RestTemplate();

    private final static Log LOG = LogFactory.getLog(VersionTxtEnvironmentCheck.class);

    private final static Pattern PATTERN = Pattern.compile("(\\w*)\\s*\\-\\s*(.*)");

    @Override
    public BuildDetails checkDeployedBuild(EnvironmentDetails environment) {
        LOG.debug(String.format("checking url %s", environment.getUrl()));
        try {
            String textFileContents = REST_TEMPLATE.getForObject(environment.getUrl().concat("/_version.txt"),String.class);
            if (textFileContents!=null) {
                Matcher matcher = PATTERN.matcher(textFileContents);
                if(matcher.matches()){
                    return new BuildDetails(matcher.group(1), matcher.group(2));
                } else {
                    LOG.warn(String.format("%s doesn't match the expected pattern",textFileContents));
                }
            }
        } catch (RestClientException e) {
            LOG.error("error checking environment " + environment.getName(),e);
        }

        return null;
    }
}
