package com.ladbrokes.domain.release;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReleaseRepository extends MongoRepository<Release,ObjectId> {

    Release findByNameIgnoreCase(String name);

}
