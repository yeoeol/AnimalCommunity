package com.community.animal.user.dto;

import java.time.LocalDateTime;

import com.community.animal.user.domain.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileDTO {
	private Long userId;
	private String username;
	private String password;
	private String email;
	private LocalDateTime createDate;

	public UserProfileDTO(User user) {
		this.userId = user.getUserId();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.email = user.getEmail();
		this.createDate = user.getCreateDate();
	}
}
