package com.example.TodoList.dtos;

import com.example.TodoList.entities.User;
import com.example.TodoList.entities.UserRoleEnum;

public class UserResponseDto {

	private String username;
	private UserRoleEnum userRoleEnum;

	public UserResponseDto( User user ) {
		this.username = user.getUsername();
		this.userRoleEnum = user.getRole();
	}
}
