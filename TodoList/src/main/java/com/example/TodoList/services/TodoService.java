package com.example.TodoList.services;

import com.example.TodoList.dtos.TodoRequestDto;
import com.example.TodoList.dtos.TodoResponseDto;
import com.example.TodoList.entities.Todo;
import com.example.TodoList.repository.TodoRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class TodoService {

	private final TodoRepository todoRepository;

	@Autowired
	public TodoService( TodoRepository todoRepository ) {
		this.todoRepository = todoRepository;
	}

	public List< TodoResponseDto > getPosts() {
		var map = todoRepository.findAll().stream().map( TodoResponseDto::new );
		var list = map.toList();

		return list;
	}

	@Transactional
	public TodoResponseDto setPost( TodoRequestDto todoRequestDto, String password ) {
		Todo recvTodo = new Todo( todoRequestDto );
		recvTodo.setPassword( password );
		Todo savedTodo = todoRepository.save( recvTodo );

		return new TodoResponseDto( savedTodo );
	}

	public TodoResponseDto getPost( String id ) {
		var post = this.findById( id );
		return new TodoResponseDto( post );
	}

	@Transactional
	public void deletePost( String id, String password ) {
		Todo todo = checkPassword( id, password );
		todoRepository.delete( todo );
	}

	@Transactional
	public TodoResponseDto updatePost( String id, TodoRequestDto todoRequestDto, String password ) {
		Todo todo = checkPassword( id, password );
		todo.update( todoRequestDto );

		Todo updatedTodo = todoRepository.save( todo );

		return new TodoResponseDto( updatedTodo );
	}

	private Todo checkPassword( String id, String password ) {
		var post = this.findById( id );
		if( !Objects.equals( post.getPassword(), password ) ) {
			throw new IllegalArgumentException( "비밀번호가 일치하지 않습니다." );
		}

		return post;
	}

	private Todo findById( String id ) {
		var post = todoRepository.findById( new ObjectId( id ) ).orElseThrow(
				() -> new IllegalArgumentException( "Can not find post by id : " + id )
		);

		return post;
	}
}
