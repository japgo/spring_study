package com.example.TodoList.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.TodoList.entities.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository< User, ObjectId > {
	Optional< User > findByUsername( String username );
}
