package com.example.TodoList.repository;

import com.example.TodoList.entities.Post;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository< Post, ObjectId > {
}
