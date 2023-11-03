package com.example.anonymousboard2.dtos;

import com.example.anonymousboard2.entities.Post;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@JsonAutoDetect
@Getter
public class PostResponseDto {
	private String id;
	private String userName;
	private String title;
	private String body;
	private LocalDateTime lastModifiedDate;
	public PostResponseDto( Post post ) {
		this.id = post.getId().toString();
		this.userName = post.getUserName();
		this.title = post.getTitle();
		this.body = post.getBody();
		this.lastModifiedDate = post.getLastModifiedDate();
	}
}
