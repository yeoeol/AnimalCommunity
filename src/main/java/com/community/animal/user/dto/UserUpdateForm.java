package com.community.animal.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateForm {
	private String username;
	private String email;
	private String password;

	@Builder
	public UserUpdateForm(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}
}
