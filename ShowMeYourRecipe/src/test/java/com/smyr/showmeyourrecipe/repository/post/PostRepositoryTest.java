package com.smyr.showmeyourrecipe.repository.post;

import com.smyr.showmeyourrecipe.entity.post.Post;
import com.smyr.showmeyourrecipe.entity.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PostRepositoryTest {

	@Autowired
	private PostRepository postRepository;

	@Test
	void findByIdAndUser() {
	}

	@Test
	void findAllByUser_Id() {
		// given
		User user = new User();
		user.setId( 1 );
		user.setUsername( "test" );

		Post post = new Post( user, "title", "content" );

		postRepository.save( post );

		// when
		List< Post > postList = postRepository.findAllByUser_Id( 1L );

		// then
		assertEquals( postList.get( 0 ).getTitle(), "title" );
	}
}