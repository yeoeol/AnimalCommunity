package com.community.animal.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.community.animal.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	List<User> findByEmail(String email);

	Optional<User> findByUsername(String username);
}
