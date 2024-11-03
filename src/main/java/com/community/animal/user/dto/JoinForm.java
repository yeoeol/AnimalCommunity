package com.community.animal.user.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class JoinForm {

	@NotEmpty
	private String email;
	@NotEmpty
	private String username;
	@NotEmpty
	private String password;
}
