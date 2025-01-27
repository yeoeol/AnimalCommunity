package com.community.animal.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.community.animal.post.domain.Post;
import com.community.animal.post.dto.PostResponse;
import com.community.animal.user.domain.User;
import com.community.animal.user.dto.LoginForm;
import com.community.animal.user.dto.JoinForm;
import com.community.animal.user.dto.UserUpdateForm;
import com.community.animal.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

	private final UserService userService;

	@GetMapping("/join")
	public String joinForm(@ModelAttribute JoinForm joinForm) {
		return "user/joinForm";
	}

	@PostMapping("/join")
	public String join(@Valid @ModelAttribute JoinForm joinForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "user/joinForm";
		}

		userService.join(joinForm);
		return "redirect:/post";
	}

	@GetMapping("/login")
	public String loginForm(@ModelAttribute LoginForm loginForm) {
		return "user/loginForm";
	}

	// @PostMapping("/login")
	// public String login(@Valid @ModelAttribute LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request) {
	// 	if (bindingResult.hasErrors()) {
	// 		return "user/loginForm";
	// 	}
	//
	// 	User loginUser = userService.login(loginForm.getEmail(), loginForm.getPassword());
	//
	// 	if (loginUser == null) {
	// 		// 아이디 또는 비밀번호가 맞지 않음
	// 		bindingResult.reject("loginFail", "이메일 또는 비밀번호가 맞지 않습니다.");
	// 		return "user/loginForm";
	// 	}
	//
	// 	// 로그인 성공 처리
	// 	//세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성
	// 	HttpSession session = request.getSession();
	// 	//세션에 로그인 회원 정보 보관
	// 	session.setAttribute(SessionConst.LOGIN_MEMBER, loginUser);
	//
	// 	return "redirect:/post";
	// }

	// @PostMapping("/logout")
	// public String logout(HttpServletRequest request) {
	// 	HttpSession session = request.getSession(false);
	// 	if (session != null) {
	// 		session.invalidate();
	// 	}
	// 	return "redirect:/post";
	// }

	@GetMapping("/profile")
	public String profile(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginUser");
		model.addAttribute("user", user);
		return "user/user_detail";
	}

	@GetMapping("/modify/{id}")
	public String updateForm(@PathVariable Long id, Model model) {
		User findUser = userService.findUserById(id);

		model.addAttribute("user", new UserUpdateForm(findUser));
		return "user/user_update";
	}

	@PostMapping("/modify/{id}")
	public String update(HttpServletRequest request, @PathVariable Long id, @ModelAttribute UserUpdateForm updateForm, Model model) {
		userService.update(id, updateForm);
		User user = userService.findUserById(id);

		HttpSession session = request.getSession();
		session.setAttribute(SessionConst.LOGIN_MEMBER, user);

		return "redirect:/user/profile";
	}
}
