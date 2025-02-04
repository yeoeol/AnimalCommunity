package com.community.animal.post.service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.community.animal.post.domain.Post;
import com.community.animal.post.dto.PostRequest;
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
	public Long savePost(PostRequest dto) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		System.out.println("email = " + email);
		User user = userRepository.findByEmail(email).orElseThrow();

		Post newPost = Post.builder()
			.postTitle(dto.getPostTitle())
			.postContent(dto.getPostContent())
			.postCategory(dto.getPostCategory())
			.postLike(0L)
			.postHit(0L)
			.user(user)
			.build();
		postRepository.save(newPost);

		return newPost.getPostId();
	}

	public Post findPostById(Long id) {
		return postRepository.findById(id)
			.orElseThrow(() -> new IllegalStateException("존재하지 않는 게시글입니다."));
	}

	@Transactional
	public void updateHits(Long postId) {
		Post post = findPostById(postId);
		post.addPostHit();
	}

	@Transactional
	public void update(Long id, PostRequest dto) {
		Post post = findPostById(id);
		post.update(dto);
	}

	@Transactional
	public void delete(Long id) {
		postRepository.deleteById(id);
	}

	public List<Post> getAllPosts() {
		return postRepository.findAll();
	}

	// 유저 접근 권한 체크
	public Boolean isAccess(Long id) {
		// 현재 로그인 되어 있는 유저의 email
		String sessionEmail = SecurityContextHolder.getContext().getAuthentication()
			.getName();
		System.out.println(sessionEmail);

		// 현재 로그인 되어 있는 유저의 role
		String sessionRole = SecurityContextHolder.getContext().getAuthentication()
			.getAuthorities().iterator().next().getAuthority();

		// 수직적으로 ADMIN이면 무조건 접근 가능
		if ("ROLE_ADMIN".equals(sessionRole)) {
			return true;
		}

		// 특정 게시글 id에 대해 본인이 작성 했는지 확인
		String postEmail = postRepository.findById(id).orElseThrow()
			.getUser().getEmail();
		if (sessionEmail.equals(postEmail)) {
			return true;
		}

		return false;
	}
}
