package com.smyr.showmeyourrecipe.entity.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private long id;
	private String username;
	private String password;
	private String email;
	private String introduce;

	@Enumerated( value = EnumType.STRING )
	private UserRoleEnum role;

	private Long kakaoId;

	public User( String nickname, String encodePassword, String email, UserRoleEnum userRoleEnum, Long kakaoId ) {
		this.username = nickname;
		this.password = encodePassword;
		this.email = email;
		this.role = userRoleEnum;
		this.kakaoId = kakaoId;
	}

	public User kakaoIdUpdate( Long kakaoId ) {
		this.kakaoId = kakaoId;

		return this;
	}
}
