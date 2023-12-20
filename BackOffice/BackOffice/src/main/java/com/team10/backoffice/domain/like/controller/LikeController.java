package com.team10.backoffice.domain.like.controller;

import com.team10.backoffice.domain.like.service.LikeService;
import com.team10.backoffice.etc.response.ApiResponse;
import com.team10.backoffice.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "좋아요", description = "좋아요 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LikeController {

    private final LikeService likeService;

    @Operation(summary = "게시글 좋아요 등록")
    @PostMapping("/posts/{postid}/likes")
    public ResponseEntity<ApiResponse> like(@PathVariable Long postid, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok(ApiResponse.ok(likeService.like(postid,userDetails.getUser().getId())));
    }

    @Operation(summary = "게시글 좋아요 취소")
    @DeleteMapping ("/posts/{postid}/likes")
    public ResponseEntity<ApiResponse> dislike(@PathVariable Long postid, @AuthenticationPrincipal UserDetailsImpl userDetails){
        likeService.dislike(postid,userDetails.getUser().getId());
        return ResponseEntity.ok(ApiResponse.ok("SUCCESS_DELETE_LIKE"));
    }


}
