package org.example.refresh_test.domain.user.repository;

import org.bson.types.ObjectId;
import org.example.refresh_test.domain.user.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository< User, String > {
}
