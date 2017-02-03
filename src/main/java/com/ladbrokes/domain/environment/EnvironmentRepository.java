package com.ladbrokes.domain.environment;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EnvironmentRepository extends MongoRepository<Environment,ObjectId> {

    Environment findByNameIgnoreCase(String name);

}
