package com.community.animal.post.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.community.animal.post.domain.Post;
import com.community.animal.post.domain.PostCategory;
import com.community.animal.post.dto.PostRequest;
import com.community.animal.post.dto.PostResponse;
import com.community.animal.post.service.PostService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

	private final PostService postService;

	@GetMapping
	public String posts(
		@RequestParam(value = "postCategory", required = false) PostCategory postCategory,
		@RequestParam(value = "postTitle", required = false) String postTitle,
		@PageableDefault(size = 3, sort = "regDate", direction = Sort.Direction.DESC) Pageable pageable,
		Model model) {

		Page<Post> postList = null;
		if (postCategory != null) {
			postList = postService.getPostsByPostCategory(postCategory, pageable);
		}
		else if (postTitle != null) {
			postList = postService.getPostsByPostTitle(postTitle, pageable);
		}
		else {
			postList = postService.getAllPosts(pageable);
		}

		// List<PostResponse> result = postList.stream()
		// 		.map(p -> new PostResponse(p))
		// 		.collect(Collectors.toList());
		Page<PostResponse> result = postList.map(PostResponse::new);
		model.addAttribute("posts", result);
		return "home";
	}

	@GetMapping("/save")
	public String createPostForm() {
		return "post/post_create";
	}

	@PostMapping("/save")
	public String createPost(@ModelAttribute PostRequest postRequest) {
		postService.savePost(postRequest);
		return "redirect:/posts";
	}

	@GetMapping("/{id}")
	public String postDetail(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") Long postId, Model model) {
		addHits(request, response, postId);
		Post findPost = postService.findPostById(postId);
		model.addAttribute("post", new PostResponse(findPost));
		return "post/post_detail";
	}

	private void addHits(HttpServletRequest request, HttpServletResponse response, Long postId) {
		Cookie oldCookie = findCookie(request, "post_hit");
		if (oldCookie != null) {
			if (!oldCookie.getValue().contains("["+postId+"]")) {
				postService.updateHits(postId);
				oldCookie.setValue(oldCookie.getValue() + "["+postId+"]");
				oldCookie.setPath("/");
				oldCookie.setMaxAge(-1);
				response.addCookie(oldCookie);
			}
		}
		else {
			postService.updateHits(postId);
			Cookie newCookie = new Cookie("post_hit", "["+postId+"]");
			newCookie.setPath("/");
			newCookie.setMaxAge(-1);
			response.addCookie(newCookie);
		}
	}

	private Cookie findCookie(HttpServletRequest request, String  cookieName) {
		Cookie oldCookie = null;

		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(cookieName)) {
					oldCookie = cookie;
				}
			}
		}
		return oldCookie;
	}

	@GetMapping("/modify/{id}")
	public String updateForm(@PathVariable Long id, Model model) {
		// 접근 권한 확인
		if (!postService.isAccess(id)) {
			return "redirect:/posts";
		}

		model.addAttribute("postUpdate", new PostResponse(postService.findPostById(id)));
		return "post/post_update";
	}

	@PostMapping("/modify/{id}")
	public String update(@PathVariable Long id, @ModelAttribute PostRequest postRequest) {
		// 접근 권한 확인
		if (!postService.isAccess(id)) {
			return "redirect:/posts";
		}

		postService.update(id, postRequest);
		return "redirect:/posts/"+id;
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		// 접근 권한 확인
		if (postService.isAccess(id)) {
			postService.delete(id);
		}

		return "redirect:/posts";
	}
}
