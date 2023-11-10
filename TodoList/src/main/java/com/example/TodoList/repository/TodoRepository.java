package com.example.TodoList.repository;

import com.example.TodoList.entities.Todo;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TodoRepository extends MongoRepository< Todo, ObjectId > {
}
