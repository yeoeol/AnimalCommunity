package com.community.animal.user.dto;

import com.community.animal.user.domain.User;

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

	public UserUpdateForm(User user) {
		this.username = user.getUsername();
		this.email = user.getEmail();
		this.password = user.getPassword();
	}
}
