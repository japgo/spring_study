package com.smyr.showmeyourrecipe.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
	private String username;
	private String password;
	private String email;
	private String introduce;
	private String role;
}
