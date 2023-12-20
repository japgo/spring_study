package com.team10.backoffice.domain.post.controller;

import com.team10.backoffice.domain.post.dto.PostRequestDto;
import com.team10.backoffice.domain.post.service.AwsS3Service;
import com.team10.backoffice.domain.post.service.PostService;
import com.team10.backoffice.etc.response.ApiResponse;
import com.team10.backoffice.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "posts", description = "게시물 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final AwsS3Service awsS3Service;


    @Operation(summary = "게시글 등록")
    @PostMapping("/posts") // 게시글 등록
    public ResponseEntity<ApiResponse> addPost(@RequestBody PostRequestDto postRequestDTO, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.addPost(postRequestDTO, userDetails.getUser());
        return ResponseEntity.ok(ApiResponse.ok("SUCCESS_ADD_POST"));
    }

    @Operation(summary = "게시글 수정")
    @PatchMapping("/posts/{id}") // 게시글 수정
    public ResponseEntity<ApiResponse> updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.updatePost(id, postRequestDto, userDetails.getUser());
        return ResponseEntity.ok(ApiResponse.ok("SUCCESS_UPDATE_POST"));
    }

    @Operation(summary = "게시글 삭제")
    @DeleteMapping("/posts/{id}") // 게시글 삭제
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.removePost(id, userDetails.getUser());
        return ResponseEntity.ok(ApiResponse.ok("SUCCESS_DELETE_POST"));
    }

    @Operation(summary = "게시글 조회")
    @GetMapping("/posts/{id}") // 게시글 조회
    public ResponseEntity<ApiResponse> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(postService.findPost(id)));
    }

    @Operation(summary = "게시글 전체 조회")
    @GetMapping("/posts") // 게시글 전체 조회
    public ResponseEntity<ApiResponse> getPosts( int page, int size ) {
        PageRequest pageRequest = PageRequest.of( page, size );
        return ResponseEntity.ok( ApiResponse.ok( postService.getPostsByPage( pageRequest ) ) );

//        return ResponseEntity.ok(ApiResponse.ok(postService.getPosts()));
    }

    @GetMapping( "/postsAll")
    public ResponseEntity< ApiResponse > getPostsAll() {
        return ResponseEntity.ok( ApiResponse.ok( postService.getPostsOrderByContentLengthDesc() ) );
    }


    @Operation(summary = "내가 작성한 게시글 조회")
    @GetMapping("/posts/myposts") // 내가 작성한 게시글
    public ResponseEntity<ApiResponse> getMyPosts(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(ApiResponse.ok(postService.getMyPosts(userDetails.getUser())));
    }

    @PostMapping( "/posts/file" )
    public ResponseEntity< ApiResponse > uploadFile(
            @RequestPart( "file" ) MultipartFile multipartFile ) throws IOException {

        return ResponseEntity.ok( ApiResponse.ok( awsS3Service.uploadFileV1( "img",  multipartFile ) ) );
    }
}
