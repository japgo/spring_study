package com.example.TodoList.entities;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document( collection = "users" )
@Getter
public class User {

	private ObjectId id;
	private String username;
	private String password;

	@Enumerated( value = EnumType.STRING )
	private UserRoleEnum role;

	public User( String username, String password ) {
		this.username = username;
		this.password = password;
		this.role = UserRoleEnum.USER;
	}
}
