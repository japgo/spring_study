package com.team10.backoffice.domain.users.dto;

import com.team10.backoffice.domain.users.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDto {
	private String username;
	private String nickname;
	private String email;
	private String introduction;

	@Builder
	public UserResponseDto( User user ) {
		this.username = user.getUsername();
		this.email = user.getEmail();
		this.introduction = user.getIntroduction();
	}
}