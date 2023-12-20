package com.team10.backoffice.domain.post.service;

import com.team10.backoffice.domain.post.dto.PostRequestDto;
import com.team10.backoffice.domain.post.entity.Post;
import com.team10.backoffice.domain.post.repository.PostQueryRepository;
import com.team10.backoffice.domain.post.repository.PostRepository;
import com.team10.backoffice.domain.users.entity.User;
import com.team10.backoffice.domain.users.entity.UserRoleEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith( MockitoExtension.class )
@ActiveProfiles( "test" )
class PostServiceTest {

	@Mock
	private PostQueryRepository postQueryRepository;
	@Mock
	private PostRepository postRepository;

	@InjectMocks
	private PostService postService;

	@Test
	void addPost() {
		// given
		User user = new User();
		user.setId( 1 );
		user.setRole( UserRoleEnum.USER );
		user.setEmail( "test@test.com" );
		user.setNickName( "nickname" );
		user.setPassword( "1234" );

		PostRequestDto postRequestDto = new PostRequestDto( "title", "content" );
		Post post = new Post( postRequestDto, user );
		when( postRepository.save( any( Post.class ) ) ).thenReturn( post );

		// when
		postService.addPost( postRequestDto, user );

		// then
		assertEquals( post.getTitle(), "title" );
	}

	@Test
	void updatePost() {
	}

	@Test
	void removePost() {
	}

	@Test
	void getPosts() {
	}

	@Test
	void getPostsOrderByContentLengthDesc() {
	}

	@Test
	void getPostsByPage() {
	}

	@Test
	void getMyPosts() {
	}

	@Test
	void findPost() {
	}
}