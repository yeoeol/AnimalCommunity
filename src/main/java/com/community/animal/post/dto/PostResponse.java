package com.community.animal.post.dto;

import java.time.LocalDateTime;

import com.community.animal.post.domain.PostCategory;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class PostResponse {
	private Long postId;
	private String username;
	private String postTitle;
	private Long postLike;
	private Long postHit;
	private LocalDateTime regDate;
	private PostCategory postCategory;
	private String postContent;

	@Builder
	public PostResponse(Long postId, String username, String postTitle, Long postLike,
		Long postHit, LocalDateTime regDate, PostCategory postCategory, String postContent) {

		this.postId = postId;
		this.username = username;
		this.postTitle = postTitle;
		this.regDate = regDate;
		this.postLike = postLike;
		this.postHit = postHit;
		this.postCategory = postCategory;
		this.postContent = postContent;
	}
}
