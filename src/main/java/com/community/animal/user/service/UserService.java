package com.community.animal.user.service;


import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.community.animal.user.domain.Role;
import com.community.animal.user.domain.User;
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


	@Transactional
	public void update(Long id, UserUpdateForm dto) {
		User user = findUserById(id);
		user.update(dto);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email).orElseThrow();

		return org.springframework.security.core.userdetails.User.builder()
			.username(user.getEmail())
			.password(user.getPassword())
			.roles(user.getRole().toString())
			.build();
	}
}
