package com.community.animal.user.service;


import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.community.animal.user.domain.User;
import com.community.animal.user.dto.CustomUserDetails;
import com.community.animal.user.dto.UserUpdateForm;
import com.community.animal.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;

	@Transactional
	public Long join(User user) {
		validateDuplicateUser(user);
		userRepository.save(user);

		return user.getUserId();
	}

	private void validateDuplicateUser(User user) {
		List<User> userList = userRepository.findByEmail(user.getEmail());
		if (!userList.isEmpty()) {
			throw new IllegalStateException("이미 존재하는 이메일입니다.");
		}
	}

	public User login(String email, String password) {
		return userRepository.findByEmail(email)
			.stream().filter(u -> u.getPassword().equals(password))
			.findFirst().orElse(null);
	}

	public User findUserById(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new IllegalStateException("없는 유저입니다."));
	}

	public UserUpdateForm toDetailUserResponse(User user) {
		return UserUpdateForm.builder()
			.userId(user.getUserId())
			.username(user.getUsername())
			.email(user.getEmail())
			.password(user.getPassword())
			.build();
	}

	@Transactional
	public void update(Long id, UserUpdateForm dto) {
		User user = findUserById(id);
		user.update(dto);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username).orElseThrow();
		return new CustomUserDetails(user);
	}
}
