package com.community.animal.post.dto;

import java.time.LocalDateTime;

import com.community.animal.post.domain.Post;
import com.community.animal.post.domain.PostCategory;

import lombok.Getter;

@Getter
public class PostResponse {
	private Long postId;
	private String username;
	private String postTitle;
	private Long postLike;
	private Long postHit;
	private LocalDateTime regDate;
	private PostCategory postCategory;
	private String postContent;

	public PostResponse(Post post) {
		this.postId = post.getPostId();
		this.username = post.getUser().getUsername();
		this.postTitle = post.getPostTitle();
		this.postLike = post.getPostLike();
		this.postHit = post.getPostHit();
		this.regDate = post.getRegDate();
		this.postCategory = post.getPostCategory();
		this.postContent = post.getPostContent();
	}
}
