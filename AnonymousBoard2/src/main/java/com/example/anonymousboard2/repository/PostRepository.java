package com.example.anonymousboard2.repository;

import com.example.anonymousboard2.entities.Post;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository< Post, ObjectId > {
}
