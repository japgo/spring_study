package com.example.security_test.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {

	@NotBlank
	private String name;

	@NotBlank
	private String password;
}
