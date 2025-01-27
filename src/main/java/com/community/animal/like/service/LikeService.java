package com.community.animal.like.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.community.animal.like.domain.Like;
import com.community.animal.like.repository.LikeRepository;
import com.community.animal.post.domain.Post;
import com.community.animal.post.repository.PostRepository;
import com.community.animal.user.domain.User;
import com.community.animal.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikeService {

	private final LikeRepository likeRepository;
	private final UserRepository userRepository;
	private final PostRepository postRepository;

	@Transactional
	public void likePost(Long postId, Long userId) {
		Post post = postRepository.findById(postId).get();
		User loginUser = userRepository.findById(userId).get();

		Like like = likeRepository.findByUserAndPost(loginUser, post).orElse(null);
		if (like == null) {
			Like newLike = Like.builder()
				.post(post)
				.user(loginUser)
				.build();
			likeRepository.save(newLike);
			post.updateLikes(post.getPostLike() + 1);
		} else {
			likeRepository.deleteById(like.getId());
			post.updateLikes(post.getPostLike() - 1);
		}
	}
}
