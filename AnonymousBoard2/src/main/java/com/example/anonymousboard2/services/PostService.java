package com.example.anonymousboard2.services;

import com.example.anonymousboard2.dtos.PostResponseDto;
import com.example.anonymousboard2.repository.PostRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

	public PostResponseDto getPost( String id ) {
		var post = postRepository.findById( new ObjectId( id ) ).orElseThrow(
				() -> new RuntimeException( "Can not find post by id : " + id )
		);

		return new PostResponseDto( post );
	}
}
