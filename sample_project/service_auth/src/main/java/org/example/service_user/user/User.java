package org.example.service_user.user;

import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Document
public class User {
	public String userName;
	public String password;
	public String email;
	public String nickName;
}
