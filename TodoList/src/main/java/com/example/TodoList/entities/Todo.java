package com.example.TodoList.entities;

import com.example.TodoList.dtos.TodoRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document( collection = "todolist" )
@Getter
@Setter
@NoArgsConstructor
public class Todo {

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

	public Todo( TodoRequestDto todoRequestDto ) {
		this.userName = todoRequestDto.getUserName();
		this.title = todoRequestDto.getTitle();
		this.content = todoRequestDto.getContent();
	}

	public Todo update( TodoRequestDto todoRequestDto ) {
		this.title = todoRequestDto.getTitle();
		this.content = todoRequestDto.getContent();

		return this;
	}
}
