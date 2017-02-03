package com.ladbrokes.domain.release.impl;

import com.ladbrokes.domain.issue.Issue;
import com.ladbrokes.domain.issue.IssueRepository;
import com.ladbrokes.domain.release.*;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of release service.
 */
@Service
public class ReleaseServiceBean implements ReleaseService {

    private ReleaseRepository releaseRepository;

    private BuildRepository buildRepository;

    private IssueRepository issueRepository;

    @Override
    @Transactional
    public ReleaseDetails registerRelease(String name, String description) {

        if(releaseRepository.findByNameIgnoreCase(name)!=null){
            throw new ReleaseException("name","another release with the same name already exists");
        }

        Release release = new Release();
        release.setName(name);
        release.setDescription(description);

        releaseRepository.save(release);

        return toReleaseDetailsDTO(release);
    }

    private ReleaseDetails toReleaseDetailsDTO(Release release) {
        ReleaseDetails details = new ReleaseDetails();
        details.setName(release.getName());
        details.setId(release.getId().toHexString());
        details.setDescription(release.getDescription());
        for(Build build:release.getReleaseBuilds()){
            details.addBuild(build.getId().toHexString());
        }
        return details;
    }

    @Override
    @Transactional(readOnly = true)
    public ReleaseDetails findRelease(String id) {
        Release release = releaseRepository.findOne(new ObjectId(id));
        if(release!=null){
            return toReleaseDetailsDTO(release);
        }
        return null;
    }

    @Override
    @Transactional
    public Collection<ReleaseDetails> findReleases() {
        List<ReleaseDetails> releaseDetails = new LinkedList<>();
        Iterable<Release> releases = releaseRepository.findAll();
        for(Release release:releases){
            releaseDetails.add(toReleaseDetailsDTO(release));
        }
        return releaseDetails;
    }

    @Override
    @Transactional
    public void updateRelease(ReleaseDetails updatedReleaseDetails) {
        Release release = releaseRepository.findOne(new ObjectId(updatedReleaseDetails.getId()));

        if(release!=null){
            Release sameNameRelease = releaseRepository.findByNameIgnoreCase(updatedReleaseDetails.getName());
            if(sameNameRelease!=null && sameNameRelease.getId()!=release.getId()){
                throw new ReleaseException("name","release with same name already exists");
            }

            release.setName(updatedReleaseDetails.getName());
            release.setDescription(updatedReleaseDetails.getDescription());

            release.getBuilds().clear();
            for(String buildId:updatedReleaseDetails.getBuildIds()){
                Build build = buildRepository.findOne(new ObjectId(buildId));
                if(build!=null){
                    release.addBuild(build);
                }
            }
        }
    }

    @Override
    @Transactional
    public BuildDetails registerReleaseBuild(String releaseId, BuildDetails buildDetails) {

        if(releaseId == null){
            throw new BuildException("release","build must be allocated to a release");
        }

        Release release = releaseRepository.findOne(new ObjectId(releaseId));
        if(release!=null){

            Build sameNumberBuild = buildRepository.findByNumberIgnoreCase(buildDetails.getBuildNumber());
            if(sameNumberBuild!=null){
                throw new BuildException("number","build with same number already exists");
            }

            Build newBuild = release.addBuild(buildDetails);
            if (newBuild!=null) {

                if (buildDetails.getIssueIds()!=null && !buildDetails.getIssueIds().isEmpty()) {
                    newBuild.setIssues(new LinkedList<>());
                    for(String issueId:buildDetails.getIssueIds()){
                        Issue issue = issueRepository.findOne(new ObjectId(issueId));
                        newBuild.getIssues().add(issue);
                    }
                }

                buildRepository.save(newBuild);
                buildDetails.setReleaseId(releaseId);
                buildDetails.setId(newBuild.getId().toHexString());
            }
        }

        return buildDetails;
    }

    @Override
    @Transactional(readOnly = true)
    public BuildDetails findBuild(String id) {
        Build build = buildRepository.findOne(new ObjectId(id));
        if(build!=null){
            return toBuildDetailsDTO(build);
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public BuildDetails findBuildByNumber(String buildNumber) {
        Build build = buildRepository.findByNumberIgnoreCase(buildNumber);
        if(build!=null){
            return toBuildDetailsDTO(build);
        }
        return null;
    }

    @Override
    @Transactional
    public BuildDetails registerUnallocatedBuild(BuildDetails detectedBuild) {
        Build build = new Build();
        build.setNumber(detectedBuild.getBuildNumber());
        build.setDescription(detectedBuild.getDescription());
        buildRepository.save(build);
        return toBuildDetailsDTO(build);
    }

    private BuildDetails toBuildDetailsDTO(Build build) {
        BuildDetails dto = new BuildDetails(build.getNumber(),build.getDescription());
        dto.setId(build.getId().toHexString());
        if (build.getRelease()!=null) {
            dto.setReleaseId(build.getRelease().getId().toHexString());
        }

        if(build.getIssues()!=null){
            List<String> issueIds = new LinkedList<>();
            for(Issue issue:build.getIssues()){
                issueIds.add(issue.getId().toHexString());
            }
            dto.setIssueIds(issueIds);
        }

        return dto;
    }

    @Override
    @Transactional
    public void updateBuild(BuildDetails buildDetails) {
        Build build = buildRepository.findOne(new ObjectId(buildDetails.getId()));
        if(build!=null){

            Build sameNumberBuild = buildRepository.findByNumberIgnoreCase(buildDetails.getBuildNumber());
            if(sameNumberBuild!=null && sameNumberBuild.getId()!=build.getId()){
                throw new BuildException("number","build with same number already exists");
            }

            build.setNumber(buildDetails.getBuildNumber());
            build.setDescription(buildDetails.getDescription());

            if(buildDetails.getReleaseId()!=null){
                Release release = releaseRepository.findOne(new ObjectId(buildDetails.getId()));
                build.setRelease(release);
            } else {
                throw new BuildException("release","build must be allocated to a release");
            }

            if (buildDetails.getIssueIds()!=null && !buildDetails.getIssueIds().isEmpty()) {
                build.getIssues().clear();
                for(String issueId:buildDetails.getIssueIds()){
                    Issue issue = issueRepository.findOne(new ObjectId(issueId));
                    build.getIssues().add(issue);
                }
            }

            buildRepository.save(build);
        }
    }

    @Autowired
    public void setReleaseRepository(ReleaseRepository releaseRepository) {
        this.releaseRepository = releaseRepository;
    }

    @Autowired
    public void setBuildRepository(BuildRepository buildRepository) {
        this.buildRepository = buildRepository;
    }

    @Autowired
    public void setIssueRepository(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }
}
