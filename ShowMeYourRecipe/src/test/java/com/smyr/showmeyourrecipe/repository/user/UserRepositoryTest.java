package com.smyr.showmeyourrecipe.repository.user;

import com.smyr.showmeyourrecipe.entity.user.User;
import com.smyr.showmeyourrecipe.entity.user.UserRoleEnum;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles( "test" )
class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	void findByUsername() {
		User user = new User();
		user.setUsername( "test" );
		user.setPassword( "1234" );
		user.setRole( UserRoleEnum.USER );
		user.setEmail( "test@test.com" );

		User savedUser = userRepository.save( user );

		assertEquals( savedUser.getUsername(), "test" );
	}

	@Test
	void findByKakaoId() {
	}

	@Test
	void findByEmail() {
	}
}