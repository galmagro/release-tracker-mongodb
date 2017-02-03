package com.ladbrokes.domain.issue;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Issue Repository.
 */
public interface IssueRepository extends MongoRepository<Issue,ObjectId> {

    Issue findByNumberIgnoreCase(String number);

}
