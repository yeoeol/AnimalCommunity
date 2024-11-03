package com.community.animal.post.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.community.animal.post.domain.Post;
import com.community.animal.post.dto.PostRequest;
import com.community.animal.post.dto.PostResponse;
import com.community.animal.post.repository.PostRepository;
import com.community.animal.user.domain.User;
import com.community.animal.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;
	private final UserRepository userRepository;

	@Transactional
	public Long savePost(PostRequest postRequest, Long loginUserId) {
		User loginUser = userRepository.findById(loginUserId)
			.orElse(null);

		Post saveEntity = Post.builder()
			.user(loginUser)
			.postTitle(postRequest.getPostTitle())
			.postContent(postRequest.getPostContent())
			.postCategory(postRequest.getPostCategory())
			.postHit(0L)
			.postLike(0L)
			.build();

		Post savedPost = postRepository.save(saveEntity);

		return savedPost.getPostId();
	}

	public Post findPostById(Long id) {
		return postRepository.findById(id)
			.orElseThrow(() -> new IllegalStateException("존재하지 않는 게시글입니다."));
	}

	@Transactional
	public void updateHits(Long id) {
		Post post = findPostById(id);
		post.setPostHit(post.getPostHit()+1);
	}

	@Transactional
	public void update(Long id, PostResponse postResponse) {
		Post post = findPostById(id);
		post.setPostTitle(postResponse.getPostTitle());
		post.setPostContent(postResponse.getPostContent());
		post.setPostCategory(postResponse.getPostCategory());
	}

	@Transactional
	public void delete(Long id) {
		postRepository.deleteById(id);
	}

	public List<PostResponse> getAllPosts() {
		return postRepository.findAll().stream()
			.map(p -> toHomePostResponse(p))
			.collect(Collectors.toList());
	}

	public PostResponse toHomePostResponse(Post post) {
		return PostResponse.builder()
			.postId(post.getPostId())
			.postTitle(post.getPostTitle())
			.username(post.getUser().getUsername())
			.postLike(post.getPostLike())
			.postHit(post.getPostHit())
			.regDate(post.getRegDate())
			.build();
	}

	public PostResponse toDetailPostResponse(Post post) {
		User user = post.getUser();

		return PostResponse.builder()
			.postId(post.getPostId())
			.postTitle(post.getPostTitle())
			.username(user.getUsername())
			.regDate(post.getRegDate())
			.postHit(post.getPostHit())
			.postContent(post.getPostContent())
			.postCategory(post.getPostCategory())
			.build();
	}
}
