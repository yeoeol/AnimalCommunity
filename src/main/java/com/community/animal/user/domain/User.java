package com.community.animal.user.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.community.animal.post.domain.Post;
import com.community.animal.post.dto.PostRequest;
import com.community.animal.user.dto.UserUpdateForm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long userId;

	private String username;
	private String password;
	private String email;

	@Enumerated(EnumType.STRING)
	private Role role;

	@CreatedDate
	private LocalDateTime createDate;

	@Builder
	public User(String username, String password, String email, Role role) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
	}

	public void update(UserUpdateForm dto) {
		this.username = dto.getUsername() == null ? this.username : dto.getUsername();
		this.password = dto.getPassword() == null ? this.password : dto.getPassword();
		this.email = dto.getEmail() == null ? this.email : dto.getEmail();
	}
}
