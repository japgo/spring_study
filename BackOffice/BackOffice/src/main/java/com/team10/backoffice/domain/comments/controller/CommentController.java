package com.team10.backoffice.domain.comments.controller;


import com.team10.backoffice.domain.comments.dto.CommentRequestDto;
import com.team10.backoffice.domain.comments.service.CommentService;
import com.team10.backoffice.domain.users.entity.User;
import com.team10.backoffice.etc.response.ApiResponse;
import com.team10.backoffice.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "comments", description = "댓글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class CommentController {
    private final CommentService commentService;

    @Operation(summary = "특정 게시물에 달린 댓글 조회")
    @GetMapping("/{postId}/comments")
    public ResponseEntity< ApiResponse > getComment( @PathVariable("postId") Long postId ) {
        return ResponseEntity.ok(ApiResponse.ok(
                commentService.getComment( postId )
        ));
    }
    @Operation(summary = "댓글 단건 조회")
    @GetMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<ApiResponse> getCommentDetail( @PathVariable("postId") Long postId,
                                                        @PathVariable("commentId") Long commentId ) {
        return ResponseEntity.ok(ApiResponse.ok(
                commentService.getCommentDetail( postId,commentId )
        ));
    }
    @Operation(summary = "댓글 작성")
    @PostMapping("/{postId}/comments")
    public ResponseEntity<ApiResponse> createComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                     @PathVariable("postId") Long postId,
                                                     @RequestBody CommentRequestDto requestDto) {
        User user = userDetails.getUser();
        commentService.createComment( user, postId, requestDto );
        return ResponseEntity.ok(ApiResponse.ok("게시글 번호 " + postId + " 에 댓글이 달렸습니다."));
    }

    @Operation(summary = "댓글 수정")
    @PatchMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<ApiResponse> updateComment(@PathVariable("postId") Long postId,
                                                     @PathVariable("commentId") Long commentId,
                                                     @RequestBody CommentRequestDto requestDto,
                                                     @AuthenticationPrincipal UserDetailsImpl userDetails )
    {
        User user = userDetails.getUser();
        commentService.updateComment( commentId, requestDto, user );
        return ResponseEntity.ok(ApiResponse.ok("댓글 번호 " + commentId + " 가 업데이트 되었습니다."));
    }

    @Operation(summary = "댓글 삭제")
    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable("postId") Long postId,
                                                     @PathVariable("commentId") Long commentId,
                                                     @AuthenticationPrincipal UserDetailsImpl userDetails )
    {
        User user = userDetails.getUser();
        commentService.deleteComment( commentId, user );
        return ResponseEntity.ok(ApiResponse.ok("댓글 번호 " + commentId + " 가 삭제 되었습니다."));
    }

    @Operation(summary = "댓글 좋아요 등록")
    @PostMapping("/{postId}/comments/{commentId}/likes")
    public ResponseEntity<ApiResponse> likeComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                   @PathVariable("postId") Long postId,
                                                   @PathVariable("commentId") Long commentId) {
        commentService.createCommentLike(userDetails.getUser(), commentId);
        return ResponseEntity.ok(ApiResponse.ok("댓글 번호 " + commentId + " 에 좋아요 가 적용되었습니다."));
    }

    @Operation(summary = "댓글 좋아요 취소")
    @DeleteMapping("/{postId}/comments/{commentId}/likes")
    public ResponseEntity<ApiResponse> deleteLikeComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                         @PathVariable("postId") Long postId,
                                                         @PathVariable("commentId") Long commentId) {
        commentService.deleteCommentLike(userDetails.getUser().getId(), commentId);
        return ResponseEntity.ok(ApiResponse.ok("댓글 번호 " + commentId + " 에 좋아요 가 해제되었습니다."));
    }
}
