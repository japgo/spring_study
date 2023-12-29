package org.example.refresh_test.domain.user.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;


@Getter
public class User {
	@Id
	private String id;
	private String username;
	private String password;

	@Builder
	public User( String username, String password ) {
		this.username = username;
		this.password = password;
	}
}
