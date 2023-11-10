package com.example.TodoList.dtos;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
public class SignupRequestDto {

	@NotBlank
	private String username;

	@NotBlank
	private String password;

	private boolean admin = false;

}
