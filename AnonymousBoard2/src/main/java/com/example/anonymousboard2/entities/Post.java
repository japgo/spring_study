package com.example.anonymousboard2.entities;

import com.example.anonymousboard2.dtos.PostRequestDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document( collection = "post" )
@Getter
@Setter
public class Post {

	@Id
	private String id;
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

	public Post( PostRequestDto postRequestDto, String password ) {
		this.userName = postRequestDto.getUserName();
		this.title = postRequestDto.getTitle();;
		this.content = postRequestDto.getContent();
		this.password = password;
	}
}
