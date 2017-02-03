package com.ladbrokes.domain.release;


import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BuildRepository extends MongoRepository<Build,ObjectId> {

    Build findByNumberIgnoreCase(String number);
}
