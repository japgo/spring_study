package com.team10.backoffice.domain.comments.service;

import com.team10.backoffice.domain.comments.dto.CommentRequestDto;
import com.team10.backoffice.domain.comments.dto.CommentResponseDto;
import com.team10.backoffice.domain.comments.entity.Comment;
import com.team10.backoffice.domain.comments.entity.CommentLike;
import com.team10.backoffice.domain.comments.entity.CommentLikeKey;
import com.team10.backoffice.domain.comments.repository.CommentLikeRepository;
import com.team10.backoffice.domain.comments.repository.CommentRepository;
import com.team10.backoffice.domain.post.entity.Post;
import com.team10.backoffice.domain.post.repository.PostRepository;
import com.team10.backoffice.domain.users.entity.User;
import com.team10.backoffice.domain.users.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.WriteAbortedException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentLikeRepository commentLikeRepository;

    public List<CommentResponseDto> getComment( Long postId ) {
        List<Comment> comments = commentRepository.findAllByPost_Id(postId);
        List<CommentLike> commentLikes = commentLikeRepository.findAll();
        List<CommentResponseDto> response = new ArrayList<>();

        for (Comment comment : comments) {
            response.add(
                    CommentResponseDto.commentResponseDtoBuilder()
                            .comment(comment)
                            .likeCount( commentLikes.size() )
                            .build());
        }
        return response;
    }

    public CommentResponseDto getCommentDetail( Long postId, Long commentId ) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new NullPointerException("해당 게시글을 찾을 수 없습니다.")
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new NullPointerException("해당 댓글을 찾을 수 없습니다.")
        );

        return CommentResponseDto.commentResponseDtoBuilder()
                .comment( comment )
                .build();
    }

    @Transactional
    public Comment createComment( User user, Long postId, CommentRequestDto requestDto) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new NullPointerException("해당 게시글을 찾을 수 없습니다.")
        );

        return commentRepository.save(Comment.commentBuilder()
                .user(user)
                .requestDto(requestDto)
                .post(post)
                .build());
    }

    @Transactional
    public Comment updateComment( Long commentId, CommentRequestDto requestDto, User user ) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new NullPointerException("해당 댓글을 찾을 수 없습니다.")
        );

        if( user.getRole() != UserRoleEnum.ADMIN ) {
            if( comment.getWriterId() != user.getId() ) {
                throw new IllegalArgumentException( "작성자가 아니면 수정 할 수 없습니다." );
            }
        }

        return comment.update(requestDto);
    }

    @Transactional
    public Comment deleteComment( Long commentId, User user ) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new NullPointerException("해당 댓글을 찾을 수 없습니다.")
        );

        if( user.getRole() != UserRoleEnum.ADMIN ) {
            if( comment.getWriterId() != user.getId() ) {
                throw new IllegalArgumentException( "작성자가 아니면 삭제 할 수 없습니다." );
            }
        }

        return comment.delete();
    }

    @Transactional
    public CommentLike createCommentLike( User user, Long commentId ) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(NoSuchElementException::new);

        return commentLikeRepository.save(
                CommentLike.builder()
                        .user(user)
                        .comment(comment)
                        .build()
        );
    }

    @Transactional
    public void deleteCommentLike(Long userId, Long commentId) {
        CommentLike commentLike = commentLikeRepository.findById(
                CommentLikeKey.builder()
                        .userId(userId)
                        .commentId(commentId)
                        .build()
        ).orElseThrow(NoSuchElementException::new);

        commentLikeRepository.delete(commentLike);
    }


}