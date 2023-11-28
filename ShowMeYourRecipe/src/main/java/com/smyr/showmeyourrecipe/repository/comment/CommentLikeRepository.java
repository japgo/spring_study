package com.smyr.showmeyourrecipe.repository.comment;

import com.smyr.showmeyourrecipe.entity.comment.CommentLike;
import com.smyr.showmeyourrecipe.entity.comment.CommentLikeKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, CommentLikeKey> {
}
