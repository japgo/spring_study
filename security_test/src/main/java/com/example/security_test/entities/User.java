package com.example.security_test.entities;

import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document( collection = "users" )
@Getter
public class User {
	private ObjectId id;
	private String name;
	private String password;

	public User( String name, String password ) {
		this.name = name;
		this.password = password;
	}
}
