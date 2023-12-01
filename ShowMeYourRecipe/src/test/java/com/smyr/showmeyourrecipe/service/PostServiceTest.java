package com.smyr.showmeyourrecipe.service;

import com.smyr.showmeyourrecipe.dto.post.PostRequest;
import com.smyr.showmeyourrecipe.entity.post.Post;
import com.smyr.showmeyourrecipe.entity.user.User;
import com.smyr.showmeyourrecipe.entity.user.UserRoleEnum;
import com.smyr.showmeyourrecipe.repository.post.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith( MockitoExtension.class )
@ActiveProfiles( "test" )
class PostServiceTest {

	@InjectMocks
	private PostService postService;

	@Mock
	private PostRepository postRepository;

	@Test
	void createPost() {
		// Given
		User user = new User();
		user.setId( 1 );
		user.setRole( UserRoleEnum.USER );
		user.setEmail( "test@test.com" );
		user.setPassword( "1234" );
		user.setIntroduce( "HI" );

		PostRequest postRequest = new PostRequest( "title", "content" );
		when( postService.createPost( user, postRequest ) )
				.thenReturn( new Post( user, "title", "content" ) );



	}

	@Test
	void updatePost() {
	}

	@Test
	void deletePost() {
	}

	@Test
	void readPost() {
	}

	@Test
	void readPostAll() {
	}

	@Test
	void readPostAllByUser() {
	}

	@Test
	void createPostLike() {
	}

	@Test
	void deletePostLike() {
	}
}