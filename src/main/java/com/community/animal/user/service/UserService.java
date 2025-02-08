package com.community.animal.user.service;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.community.animal.user.domain.Role;
import com.community.animal.user.domain.User;
import com.community.animal.user.dto.CustomUserDetails;
import com.community.animal.user.dto.JoinForm;
import com.community.animal.user.dto.UserUpdateForm;
import com.community.animal.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Transactional
	public Long join(JoinForm dto) {
		validateDuplicateUser(dto.getEmail());
		User user = User.builder()
			.email(dto.getEmail())
			.username(dto.getUsername())
			.password(bCryptPasswordEncoder.encode(dto.getPassword()))
			.role(Role.USER)
			.build();

		userRepository.save(user);
		return user.getUserId();
	}

	private void validateDuplicateUser(String email) {
		User findUser = userRepository.findByEmail(email).orElse(null);
		if (findUser != null) {
			throw new IllegalStateException("이미 존재하는 이메일입니다.");
		}
	}

	// public User login(String email, String password) {
	// 	return userRepository.findByEmail(email)
	// 		.stream().filter(u -> u.getPassword().equals(password))
	// 		.findFirst().orElse(null);
	// }

	public User findUserById(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new IllegalStateException("없는 유저입니다."));
	}

	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email)
			.orElseThrow(() -> new IllegalStateException("없는 유저입니다."));
	}


	@Transactional
	public void update(Long id, UserUpdateForm dto) {
		User user = findUserById(id);
		String newEncodePw = bCryptPasswordEncoder.encode(dto.getPassword());
		user.update(dto.getUsername(), newEncodePw);
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
		String postEmail = userRepository.findById(id).orElseThrow()
			.getEmail();
		if (sessionEmail.equals(postEmail)) {
			return true;
		}

		return false;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email).orElseThrow();
		return new CustomUserDetails(user);
	}
}
