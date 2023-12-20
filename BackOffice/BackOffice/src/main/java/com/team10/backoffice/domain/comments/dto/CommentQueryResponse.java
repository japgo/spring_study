package com.team10.backoffice.domain.comments.dto;

import com.team10.backoffice.domain.comments.entity.CommentLike;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentQueryResponse {
    private final CommentLike commentLike;
    private final boolean myLike;

    @Builder
    public CommentQueryResponse( CommentLike commentLike, boolean myLike) {
        this.commentLike = commentLike;
        this.myLike = myLike;
    }
}