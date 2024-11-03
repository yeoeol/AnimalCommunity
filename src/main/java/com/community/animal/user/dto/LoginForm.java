package com.community.animal.user.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class LoginForm {

	@NotEmpty
	private String email;
	@NotEmpty
	private String password;
}
