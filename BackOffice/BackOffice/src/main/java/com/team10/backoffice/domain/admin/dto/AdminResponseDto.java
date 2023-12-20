package com.team10.backoffice.domain.admin.dto;

import com.team10.backoffice.domain.users.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminResponseDto {
    private String username;
    private String nickname;
    private String password;
    private String email;
    private String introduction;

    @Builder
    public AdminResponseDto(User user) {
        this.username = user.getUsername();
        this.nickname = user.getNickName();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.introduction = user.getIntroduction();
    }
}