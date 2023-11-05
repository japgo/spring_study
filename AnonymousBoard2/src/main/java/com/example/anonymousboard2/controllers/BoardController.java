package com.example.anonymousboard2.controllers;

import com.example.anonymousboard2.dtos.PostRequestDto;
import com.example.anonymousboard2.dtos.PostResponseDto;
import com.example.anonymousboard2.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
	public ResponseEntity< List< PostResponseDto > > getPosts() {
		var ps = postService.getPosts();
		return ResponseEntity.ok( ps );
	}

	@PostMapping( "/board" )
	public ResponseEntity< PostResponseDto > setPost( PostRequestDto postRequestDto, @RequestHeader String password ) {

		var ret = postService.setPost( postRequestDto, password );

		return ResponseEntity.ok()
				.header( "headerName", "headerValue" )
				.body( ret );
	}

	@GetMapping( "/board/{id}" )
	public ResponseEntity< PostResponseDto > getPost( @PathVariable String id ) {
		HttpStatus httpStatus;
		PostResponseDto postResponseDto = null;

		try {
			postResponseDto = postService.getPost( id );
			httpStatus = HttpStatus.OK;
		}
		catch( IllegalArgumentException ex ) {
			httpStatus = HttpStatus.BAD_REQUEST;
		}
		catch( Exception ex ) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return ResponseEntity.status( httpStatus ).body( postResponseDto );
	}

	@DeleteMapping( "/board/{id}" )
	public ResponseEntity< String > deletePost( @PathVariable String id, @RequestHeader String password ) {
		postService.deletePost( id, password );

		return ResponseEntity.ok( "delete success" );
	}

	@PatchMapping( "/board/{id}" )
	public ResponseEntity< PostResponseDto > updatePost( @PathVariable String id, PostRequestDto postRequestDto, @RequestHeader String password ) {
		PostResponseDto postResponseDto = postService.updatePost( id, postRequestDto, password );

		return ResponseEntity.ok( postResponseDto );
	}

}
