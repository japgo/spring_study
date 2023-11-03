package com.example.anonymousboard2.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document( collection = "post" )
@Getter
@Setter
public class Post {
	String userName;
	String title;
	String body;

	@Override
	public String toString() {
		return "name is " + userName;
	}
}
