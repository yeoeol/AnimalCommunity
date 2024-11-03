package com.community.animal.post.dto;

import com.community.animal.post.domain.PostCategory;
import com.community.animal.user.domain.User;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostRequest {
	private String postTitle;
	private String postContent;
	private PostCategory postCategory;
}
