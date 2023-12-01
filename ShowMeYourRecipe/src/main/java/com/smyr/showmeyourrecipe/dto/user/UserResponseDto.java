package com.smyr.showmeyourrecipe.dto.user;

import com.smyr.showmeyourrecipe.entity.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDto {
	private String username;
	private String email;
	private String introduce;

	@Builder
	public UserResponseDto( User user ) {
		this.username = user.getUsername();
		this.email = user.getEmail();
		this.introduce = user.getIntroduce();
	}
}
