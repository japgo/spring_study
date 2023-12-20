package com.team10.backoffice.domain.comments.entity;

import com.team10.backoffice.domain.comments.dto.CommentRequestDto;
import com.team10.backoffice.domain.etc.Timestamped;
import com.team10.backoffice.domain.post.entity.Post;
import com.team10.backoffice.domain.users.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode( callSuper = false )
@Table(name = "comment")
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(nullable = false)
    private Long writerId;

    @Column(nullable = false)
    private String writerName;

    @Column(nullable = false)
    private String content;

    @Column(nullable = true)
    private Long parentCommentId;

    @Column(nullable = true)
    private Long depth;

    @Column(nullable = true)
    private int likeCount;

    @Column(nullable = true)
    private String recentLikeUser;

    @Column(nullable = true)
    private Boolean myLike;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;


    @Builder(builderClassName = "commentBuilder", builderMethodName = "commentBuilder")
    public Comment( User user, CommentRequestDto requestDto, Post post ) {
        this.writerId = user.getId();
        this.writerName = user.getUsername();
        this.content = requestDto.getContent();
        this.post = post;
        this.depth = 1L;
        this.likeCount = 0;
        this.recentLikeUser = null;
        this.myLike = false;
    }

    @Builder(builderClassName = "replyBuilder", builderMethodName = "replyBuilder")
    public Comment( User user, CommentRequestDto requestDto, Post post, Long parentCommentId, Long depth ) {
        this.writerId = user.getId();
        this.writerName = user.getUsername();
        this.content = requestDto.getContent();
        this.post = post;
        this.parentCommentId = parentCommentId;
        this.depth = depth + 1;
    }

    public Comment update(CommentRequestDto requestDto) {
        this.content = requestDto.getContent();
        return this;
    }

    public Comment delete() {
        this.content = "삭제된 댓글입니다.";
        return this;
    }
}
