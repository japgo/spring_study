package com.team10.backoffice.domain.post.repository;

import com.team10.backoffice.domain.post.entity.Post;
import com.team10.backoffice.domain.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByUser(User user);
}
