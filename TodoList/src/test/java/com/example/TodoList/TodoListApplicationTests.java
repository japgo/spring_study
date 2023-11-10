package com.example.TodoList;

import com.example.TodoList.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TodoListApplicationTests {

	@Autowired
	private TodoRepository todoRepository;

	@Test
	void contextLoads() {
		return;

		//Post post = new Post();
		//post.setUserName( "나야" );
		//post.setTitle( "제목이야" );
		//post.setBody( "body ..." );

		//postRepository.save( post );

		//System.out.println( "=======================");
		//System.out.println( postRepository.findAll() );
	}

}
