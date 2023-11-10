package com.example.TodoList.entities;

import com.example.TodoList.dtos.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document( collection = "board" )
@Getter
@Setter
@NoArgsConstructor
public class Post {

	@Id
	private ObjectId id;

	private String userName;
	private String title;
	private String content;
	private String password;

	@CreatedDate
	private LocalDateTime createdDate;

	@LastModifiedDate
	private LocalDateTime lastModifiedDate;

	@Override
	public String toString() {
		return "name is " + userName;
	}

	public Post( PostRequestDto postRequestDto ) {
		this.userName = postRequestDto.getUserName();
		this.title = postRequestDto.getTitle();
		this.content = postRequestDto.getContent();
	}

	public Post update( PostRequestDto postRequestDto ) {
		this.title = postRequestDto.getTitle();
		this.content = postRequestDto.getContent();

		return this;
	}
}
