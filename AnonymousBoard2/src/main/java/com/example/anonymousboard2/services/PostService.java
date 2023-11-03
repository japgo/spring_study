package com.example.anonymousboard2.services;

import com.example.anonymousboard2.dtos.PostResponseDto;
import com.example.anonymousboard2.repository.PostRepository;
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
		return null;
	}
}
