package com.example.anonymousboard2;

import com.example.anonymousboard2.entities.Post;
import com.example.anonymousboard2.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AnonymousBoard2ApplicationTests {

	@Autowired
	private PostRepository postRepository;

	@Test
	void contextLoads() {
		Post post = new Post();
		post.setUserName( "나야" );
		post.setTitle( "제목이야" );
		post.setBody( "body ..." );

		postRepository.save( post );

		System.out.println( "=======================");
		System.out.println( postRepository.findAll() );
	}

}
