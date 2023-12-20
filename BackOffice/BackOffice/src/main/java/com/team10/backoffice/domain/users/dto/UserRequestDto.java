package com.team10.backoffice.domain.users.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UserRequestDto {
	@Pattern(regexp = "^[a-z0-9]{4,10}$", message = "소문자 5~20자로 구성해주세요")
	private String username;

	@Pattern(regexp = "^[a-zA-Z0-9/가-힣]{2,30}$", message = "한글, 알파벳 대,소문자, 숫자로 구성해주세요")
	private String nickname;

	//@Pattern(regexp = "^[A-Za-z\\d@$!%*?&]{8,15}$", message = "알파벳 대소문자, 특수문자 8~15자로 구성해주세요")
	private String password;

	@Pattern(regexp = "^([a-z0-9]+)@([\\da-z\\.-]+)\\.([a-z\\.]{1,50})$", message = "이메일 형식으로 작성해주세요")
	private String email;

	@Size(max = 255, message = "최대 255자까지 작성 가능합니다")
	private String introduction;

	private String role;
}