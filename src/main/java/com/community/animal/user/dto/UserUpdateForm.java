package com.community.animal.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateForm {
	private Long userId;
	private String username;
	private String email;
	private String password;

	@Builder
	public UserUpdateForm(Long userId, String username, String email, String password) {
		this.userId = userId;
		this.username = username;
		this.email = email;
		this.password = password;
	}
}
