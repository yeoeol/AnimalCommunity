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
import lombok.Setter;

@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User {

	@Id @GeneratedValue
	@Column(name = "user_id")
	private Long userId;

	private String username;
	private String password;
	private String email;

	@Enumerated(EnumType.STRING)
	private Role role;

	private LocalDateTime createDate;

	@Builder
	public User(Long userId, String username, String password, String email, Role role, LocalDateTime createDate) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
		this.createDate = createDate;
	}
}
