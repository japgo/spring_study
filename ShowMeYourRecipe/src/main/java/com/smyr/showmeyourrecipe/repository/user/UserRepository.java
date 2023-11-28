package com.smyr.showmeyourrecipe.repository.user;

import com.smyr.showmeyourrecipe.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository< User, Long > {
	Optional< User > findByUsername( String username );
}
