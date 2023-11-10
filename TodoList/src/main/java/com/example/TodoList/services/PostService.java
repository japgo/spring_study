package com.example.TodoList.services;

import com.example.TodoList.dtos.PostRequestDto;
import com.example.TodoList.dtos.PostResponseDto;
import com.example.TodoList.entities.Post;
import com.example.TodoList.repository.PostRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class PostService {

	private final PostRepository postRepository;

	@Autowired
	public PostService( PostRepository postRepository ) {
		this.postRepository = postRepository;
	}

	public List< PostResponseDto > getPosts() {
		var map = postRepository.findAll().stream().map( PostResponseDto::new );
		var list = map.toList();

		return list;
	}

	@Transactional
	public PostResponseDto setPost( PostRequestDto postRequestDto, String password ) {
		Post recvPost = new Post( postRequestDto );
		recvPost.setPassword( password );
		Post savedPost = postRepository.save( recvPost );

		return new PostResponseDto( savedPost );
	}

	public PostResponseDto getPost( String id ) {
		var post = this.findById( id );
		return new PostResponseDto( post );
	}

	@Transactional
	public void deletePost( String id, String password ) {
		Post post = checkPassword( id, password );
		postRepository.delete( post );
	}

	@Transactional
	public PostResponseDto updatePost( String id, PostRequestDto postRequestDto, String password ) {
		Post post = checkPassword( id, password );
		post.update( postRequestDto );

		Post updatedPost = postRepository.save( post );

		return new PostResponseDto( updatedPost );
	}

	private Post checkPassword( String id, String password ) {
		var post = this.findById( id );
		if( !Objects.equals( post.getPassword(), password ) ) {
			throw new IllegalArgumentException( "비밀번호가 일치하지 않습니다." );
		}

		return post;
	}

	private Post findById( String id ) {
		var post = postRepository.findById( new ObjectId( id ) ).orElseThrow(
				() -> new IllegalArgumentException( "Can not find post by id : " + id )
		);

		return post;
	}
}
