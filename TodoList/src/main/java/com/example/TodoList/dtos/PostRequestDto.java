package com.example.TodoList.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequestDto {
	private String userName;
	private String title;
	private String content;
}
