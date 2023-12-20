package com.team10.backoffice.domain.users.dto;

import lombok.Getter;
import jakarta.validation.constraints.Pattern;

@Getter
public class UserPasswordDto {
    String currentPassword;

    @Pattern(regexp = "^[A-Za-z\\d@$!%*?&]{8,15}$", message = "알파벳 대소문자, 특수문자 8~15자로 구성해주세요")
    String newPassword;

    String checkPassword;
}