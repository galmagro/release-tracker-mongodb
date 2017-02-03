package com.ladbrokes.domain.check;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;

/**
 * Repository for checks.
 */
public interface CheckRepository extends MongoRepository<Check,ObjectId> {

    Check findTopByEnvironmentIdOrderByCheckTimeDesc(ObjectId environmentId);

    Check findTopByEnvironmentIdAndBuildIdOrderByCheckTimeAsc(ObjectId environmentId, ObjectId buildId);

    Long deleteByEnvironmentIdAndCheckTimeLessThanEqual(ObjectId environmentId, Date checkTime);

}
