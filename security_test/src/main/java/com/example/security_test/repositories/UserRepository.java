package com.example.security_test.repositories;

import com.example.security_test.entities.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository< User, ObjectId > {
	User findByName( String name );
}
