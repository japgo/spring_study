package com.example.TodoList.dtos;

import com.example.TodoList.entities.Post;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@JsonAutoDetect
@Getter
@Setter
public class PostResponseDto {
	private String id;
	private String userName;
	private String title;
	private String content;
	private LocalDateTime lastModifiedDate;
	public PostResponseDto( Post post ) {
		this.id = post.getId().toString();
		this.userName = post.getUserName();
		this.title = post.getTitle();
		this.content = post.getContent();
		this.lastModifiedDate = post.getLastModifiedDate();
	}
}
