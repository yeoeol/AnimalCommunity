package com.community.animal.post.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.community.animal.post.domain.Post;
import com.community.animal.post.dto.PostRequest;
import com.community.animal.post.dto.PostResponse;
import com.community.animal.post.service.PostService;
import com.community.animal.user.controller.SessionConst;
import com.community.animal.user.domain.User;
import com.community.animal.user.dto.LoginForm;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

	private final PostService postService;

	@GetMapping
	public String posts(Model model) {
		List<PostResponse> posts = postService.getAllPosts();

		model.addAttribute("posts", posts);
		return "home";
	}

	@GetMapping("/save")
	public String uploadForm(
		@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) User loginUser, Model model) {

		if (loginUser == null) {
			model.addAttribute("loginForm", new LoginForm());
			return "redirect:/user/login";
		}

		return "post/post_create";
	}

	@PostMapping("/save")
	public String upload(
		@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) User loginUser,
		@ModelAttribute PostRequest postRequest) {

		postService.savePost(postRequest, loginUser.getUserId());
		return "redirect:/post";
	}

	@GetMapping("/{id}")
	public String postDetail(@PathVariable Long id, Model model) {
		postService.updateHits(id);
		Post findPost = postService.findPostById(id);
		PostResponse postResponse = postService.toDetailPostResponse(findPost);

		model.addAttribute("post", postResponse);

		return "post/post_detail";
	}

	@GetMapping("/modify/{id}")
	public String updateForm(@PathVariable Long id, Model model) {
		Post findPost = postService.findPostById(id);
		PostResponse postResponse = postService.toDetailPostResponse(findPost);
		model.addAttribute("postUpdate", postResponse);
		return "post/post_update";
	}

	@PostMapping("/modify/{id}")
	public String update(@PathVariable Long id, @ModelAttribute PostResponse postResponse, Model model) {
		postService.update(id, postResponse);
		Post post = postService.findPostById(id);
		model.addAttribute("post", post);
		return "redirect:/post/"+id;
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		postService.delete(id);
		return "redirect:/post";
	}
}
