package com.team10.backoffice.domain.comments.repository;


import com.team10.backoffice.domain.comments.entity.CommentLike;
import com.team10.backoffice.domain.comments.entity.CommentLikeKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository< CommentLike, CommentLikeKey > {
}
