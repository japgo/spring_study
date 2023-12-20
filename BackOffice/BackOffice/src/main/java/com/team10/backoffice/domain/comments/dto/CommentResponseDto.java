package com.team10.backoffice.domain.comments.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team10.backoffice.domain.comments.entity.Comment;
import com.team10.backoffice.domain.users.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private final Long commentId;
    private final Long parentCommentId;
    private final String content;
    private final Long postId;
    private final Long depth;
    private final Long writerId;
    private final String writer;
    private final int likeCount;
    private final String recentLikeUser;
    private final Boolean myLike;

    @JsonFormat( pattern = "yyyy-MM-dd" )
    private final LocalDateTime lastModifiedDate;


//    @Builder(builderClassName = "commentResponseDtoBuilder", builderMethodName = "commentResponseDtoBuilder")
//    public CommentResponseDto(CommentQueryResponse res, int likeCount) {
//        Comment comment = res.getCommentLike().getComment();
//        User user = res.getCommentLike().getUser();
//        User commentUser = comment.getPost().getUser();
//
//        this.commentId = comment.getCommentId();
//        this.parentCommentId = comment.getParentCommentId();
//        this.content = comment.getContent();
//        this.postId = comment.getPost().getId();
//        this.depth = comment.getDepth();
//        this.writerId = commentUser.getId();
//        this.writer = commentUser.getUsername();
//        this.likeCount = likeCount;
//        this.recentLikeUser = user.getUsername();
//        this.myLike = res.isMyLike();
//        this.lastModifiedDate = comment.getLastModifiedDate();
//    }

    @Builder(builderClassName = "commentResponseDtoBuilder", builderMethodName = "commentResponseDtoBuilder")
    public CommentResponseDto( Comment comment, int likeCount ) {
        this.commentId = comment.getCommentId();
        this.content = comment.getContent();
        this.parentCommentId = comment.getParentCommentId();
        this.postId = comment.getPost().getId();
        this.depth = comment.getDepth();
        this.writerId = comment.getWriterId();
        this.writer = comment.getWriterName();
        this.likeCount = likeCount;
        this.recentLikeUser = comment.getRecentLikeUser();
        this.myLike = comment.getMyLike();
        this.lastModifiedDate = comment.getModifiedAt();
    }
}