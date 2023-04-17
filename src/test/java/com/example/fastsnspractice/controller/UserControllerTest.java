package com.example.fastsnspractice.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.fastsnspractice.controller.request.UserJoinRequest;
import com.example.fastsnspractice.controller.request.UserLoginRequest;
import com.example.fastsnspractice.exception.SnsApplicationException;
import com.example.fastsnspractice.model.User;
import com.example.fastsnspractice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private UserService userService;

	@Test
	@DisplayName("회원가입")
	void join_success() throws Exception{

	    // given
		String userName = "userName";
		String password = "password";

		when(userService.join(userName,password)).thenReturn(mock(User.class));

	    // when

		mockMvc.perform(post("/api/v1/users/join").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new UserJoinRequest(userName, password)))
		).andDo(print())
			.andExpect(status().isOk());


	    // then
	}


	@Test
	@DisplayName("회원가입시 에러 발생")
	void join_fail() throws Exception{

		// given
		String userName = "userName";
		String password = "password";


		when(userService.join(userName,password)).thenThrow(new SnsApplicationException());

		// when

		mockMvc.perform(post("/api/v1/users/join").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new UserJoinRequest(userName, password)))
			).andDo(print())
			.andExpect(status().isConflict());


		// then
	}


	@Test
	@DisplayName("로그인")
	void login_success() throws Exception{

		// given
		String userName = "userName";
		String password = "password";

		when(userService.login(userName, password)).thenReturn("");

		// when

		mockMvc.perform(post("/api/v1/users/login").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new UserLoginRequest(userName, password)))
			).andDo(print())
			.andExpect(status().isOk());


		// then
	}

	@Test
	@DisplayName("로그인시 회원가입이 안 된 username일시 에러 반환")
	void login_failure_with_wrong_username() throws Exception{

		// given
		String userName = "userName";
		String password = "password";

		when(userService.login(userName, password)).thenThrow(new SnsApplicationException());

		// when

		mockMvc.perform(post("/api/v1/users/login").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new UserLoginRequest(userName, password)))
			).andDo(print())
			.andExpect(status().isNotFound());


		// then
	}

	@Test
	@DisplayName("로그인시 틀린 패스워드 입력시 에러반환")
	void login_failure_with_wrong_password() throws Exception{

		// given
		String userName = "userName";
		String password = "password";

		when(userService.login(userName, password)).thenThrow(new SnsApplicationException());

		// when

		mockMvc.perform(post("/api/v1/users/login").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new UserLoginRequest(userName, password)))
			).andDo(print())
			.andExpect(status().isUnauthorized());

		// then
	}


}



