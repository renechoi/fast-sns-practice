package com.example.fastsnspractice.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.fastsnspractice.controller.request.UserJoinRequest;
import com.example.fastsnspractice.controller.response.Response;
import com.example.fastsnspractice.controller.response.UserJoinResponse;
import com.example.fastsnspractice.model.User;
import com.example.fastsnspractice.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;


	@PostMapping("/join")
	public Response<UserJoinResponse> join(@RequestBody UserJoinRequest request) {
		User user = userService.join(request.getUserName(), request.getUserName());

		return Response.success(UserJoinResponse.fromUser(user));
	}
}
