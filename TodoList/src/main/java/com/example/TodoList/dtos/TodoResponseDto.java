package com.example.TodoList.dtos;

import com.example.TodoList.entities.Todo;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@JsonAutoDetect
@Getter
@Setter
public class TodoResponseDto {
	private String id;
	private String userName;
	private String title;
	private String content;
	private LocalDateTime lastModifiedDate;
	public TodoResponseDto( Todo todo ) {
		this.id = todo.getId().toString();
		this.userName = todo.getUserName();
		this.title = todo.getTitle();
		this.content = todo.getContent();
		this.lastModifiedDate = todo.getLastModifiedDate();
	}
}
