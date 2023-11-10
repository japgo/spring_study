package com.example.TodoList.controllers;

import com.example.TodoList.dtos.TodoRequestDto;
import com.example.TodoList.dtos.TodoResponseDto;
import com.example.TodoList.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "/api" )
public class BoardController {

	private final TodoService todoService;

	@Autowired
	public BoardController( TodoService todoService ) {
		this.todoService = todoService;
	}

	@GetMapping( "/board" )
	public ResponseEntity< List< TodoResponseDto > > getPosts() {
		var ps = todoService.getPosts();
		return ResponseEntity.ok( ps );
	}

	@PostMapping( "/board" )
	public ResponseEntity< TodoResponseDto > setPost( TodoRequestDto todoRequestDto, @RequestHeader String password ) {

		var ret = todoService.setPost( todoRequestDto, password );

		return ResponseEntity.ok()
				.header( "headerName", "headerValue" )
				.body( ret );
	}

	@GetMapping( "/board/{id}" )
	public ResponseEntity< TodoResponseDto > getPost( @PathVariable String id ) {
		HttpStatus httpStatus;
		TodoResponseDto todoResponseDto = null;

		try {
			todoResponseDto = todoService.getPost( id );
			httpStatus = HttpStatus.OK;
		}
		catch( IllegalArgumentException ex ) {
			httpStatus = HttpStatus.BAD_REQUEST;
		}
		catch( Exception ex ) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return ResponseEntity.status( httpStatus ).body( todoResponseDto );
	}

	@DeleteMapping( "/board/{id}" )
	public ResponseEntity< String > deletePost( @PathVariable String id, @RequestHeader String password ) {
		todoService.deletePost( id, password );

		return ResponseEntity.ok( "delete success" );
	}

	@PatchMapping( "/board/{id}" )
	public ResponseEntity< TodoResponseDto > updatePost( @PathVariable String id, TodoRequestDto todoRequestDto, @RequestHeader String password ) {
		TodoResponseDto todoResponseDto = todoService.updatePost( id, todoRequestDto, password );

		return ResponseEntity.ok( todoResponseDto );
	}

}
