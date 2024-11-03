package com.community.animal.post.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.community.animal.post.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
	@EntityGraph(attributePaths = {"user"})
	List<Post> findAll();

	@EntityGraph(attributePaths = {"user"})
	Optional<Post> findById(Long id);
}
