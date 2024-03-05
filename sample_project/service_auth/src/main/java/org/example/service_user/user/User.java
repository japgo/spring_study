package org.example.service_user.user;

import lombok.Builder;

@Builder
public class User {
	public String userName;
	public String password;
	public String email;
	public String nickName;
}
