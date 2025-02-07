package com.community.animal.post.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.community.animal.post.domain.Post;
import com.community.animal.post.domain.PostCategory;

public interface PostRepository extends JpaRepository<Post, Long> {

	@EntityGraph(attributePaths = {"user"})
	Page<Post> findAll(Pageable pageable);
	// List<Post> findAll();

	@EntityGraph(attributePaths = {"user"})
	Optional<Post> findById(Long id);

	@EntityGraph(attributePaths = {"user"})
	Page<Post> findAllByPostCategory(PostCategory postCategory, Pageable pageable);
	// List<Post> findAllByPostCategory(PostCategory postCategory);

	@EntityGraph(attributePaths = {"user"})
	Page<Post> findAllByPostTitleContaining(String postTitle, Pageable pageable);
	// List<Post> findAllByPostTitleContaining(String postTitle);

}
