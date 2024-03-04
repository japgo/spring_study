package org.example.service_auth.user;

import lombok.Builder;

@Builder
public class User {
	public String userName;
	public String password;
	public String email;
	public String nickName;
}
