package com.community.animal.like.dto;

import com.community.animal.post.domain.Post;
import com.community.animal.user.domain.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikeRequestDTO {
	private User user;
	private Post post;
}
