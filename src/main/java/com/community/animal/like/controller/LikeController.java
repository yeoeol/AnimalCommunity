package com.community.animal.like.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.community.animal.like.service.LikeService;
import com.community.animal.user.dto.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LikeController {

	private final LikeService likeService;

	@GetMapping("/posts/{id}/likes")
	public String addLike(@PathVariable("id") Long postId, @AuthenticationPrincipal CustomUserDetails userDetails) {
		Long userId = userDetails.getUserId();
		likeService.likePost(postId, userId);
		return "redirect:/posts/"+postId;
	}
}
