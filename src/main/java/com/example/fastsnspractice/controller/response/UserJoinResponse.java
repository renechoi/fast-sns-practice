package com.example.fastsnspractice.controller.response;

import com.example.fastsnspractice.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public
class UserJoinResponse {
	private Integer id;
	private String name;

	public static UserJoinResponse fromUser(User user) {
		return new UserJoinResponse(
			user.getId(),
			user.getUsername()
		);
	}

}
