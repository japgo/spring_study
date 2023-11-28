package com.smyr.showmeyourrecipe.repository.post;

import com.smyr.showmeyourrecipe.entity.user.User;
import com.smyr.showmeyourrecipe.entity.post.Post;
import com.smyr.showmeyourrecipe.entity.post.PostLike;
import com.smyr.showmeyourrecipe.entity.post.PostLikePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface PostLikeRepository extends JpaRepository<PostLike, PostLikePK> {
    void deletePostLikeByUserAndPost(User user, Post post);

    List<PostLike> findByPost_IdOrderByCreatedAtDesc(Long postId);

    List<PostLike> findByPost_IdAndUser_Id(Long postId, Long userId);
}