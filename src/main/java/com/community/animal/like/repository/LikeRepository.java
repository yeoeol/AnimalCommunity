package com.community.animal.like.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.community.animal.like.domain.Like;
import com.community.animal.post.domain.Post;
import com.community.animal.user.domain.User;

public interface LikeRepository extends JpaRepository<Like, Long> {
	Optional<Like> findByUserAndPost(User user, Post post);
}
