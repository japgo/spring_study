package com.team10.backoffice.domain.users.repository;

import com.team10.backoffice.domain.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository< User, Long > {
	Optional< User > findByUsername( String username );

	Optional< User > findByKakaoId( Long kakaoId );

	Optional< User > findByEmail( String email );

	boolean existsByUsernameOrEmailOrNickName(String username, String email, String nickname);
}
