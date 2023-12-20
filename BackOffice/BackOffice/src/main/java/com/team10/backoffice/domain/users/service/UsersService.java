package com.team10.backoffice.domain.users.service;

import com.team10.backoffice.domain.users.dto.UserRequestDto;
import com.team10.backoffice.domain.users.dto.UserResponseDto;

public interface UsersService {

	void signup( UserRequestDto userRequestDto );
	UserResponseDto getUser( String identifier );

}
