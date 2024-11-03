package com.community.animal.user.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {

	@Id @GeneratedValue
	@Column(name = "user_id")
	private Long userId;

	private String username;
	private String email;
	private String password;

	@Enumerated(EnumType.STRING)
	private Role role;

	private LocalDateTime createDate;

	@Builder
	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.createDate = LocalDateTime.now();
		this.role = Role.USER;
	}
}
