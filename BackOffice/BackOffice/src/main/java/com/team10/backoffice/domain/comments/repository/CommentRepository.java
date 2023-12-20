package com.team10.backoffice.domain.comments.repository;


import com.team10.backoffice.domain.comments.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository< Comment, Long> {
	List<Comment> findAllByPost_Id(Long postId);
	List<Comment> findByWriterIdAndPost_Id( Long writerId, Long postId );
}