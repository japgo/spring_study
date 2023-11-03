package com.example.anonymousboard2.controllers;

import com.example.anonymousboard2.dtos.PostResponseDto;
import com.example.anonymousboard2.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping( "/api" )
public class BoardController {

	private final PostService postService;

	@Autowired
	public BoardController( PostService postService ) {
		this.postService = postService;
	}

	@GetMapping( "/board" )
	public List< PostResponseDto > getPosts() {
		return postService.getPosts();
	}
}
