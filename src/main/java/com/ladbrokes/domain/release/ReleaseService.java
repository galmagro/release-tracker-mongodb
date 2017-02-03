package com.ladbrokes.domain.release;

import java.util.Collection;

/**
 * Release related operations.
 */
public interface ReleaseService {

    ReleaseDetails registerRelease(String name, String description);

    ReleaseDetails findRelease(String id);

    Collection<ReleaseDetails> findReleases();

    void updateRelease(ReleaseDetails updatedReleaseDetails);

    BuildDetails registerReleaseBuild(String releaseId, BuildDetails buildDetails);

    BuildDetails findBuild(String id);

    BuildDetails findBuildByNumber(String buildNumber);

    void updateBuild(BuildDetails buildDetails);

    BuildDetails registerUnallocatedBuild(BuildDetails detectedBuild);
}
