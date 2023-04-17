package com.example.fastsnspractice.service;

import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.fastsnspractice.repository.UserEntityRepository;
import com.example.fastsnspractice.exception.SnsApplicationException;
import com.example.fastsnspractice.fixture.UserEntityFixture;
import com.example.fastsnspractice.model.entity.UserEntity;

@SpringBootTest
public class UserServiceTest {

	@Autowired
	private UserService userService;

	@MockBean
	private UserEntityRepository userEntityRepository;

	@Test
	@DisplayName("회원가입 정상 동장")
	void join_success() throws Exception{

	    // given
		String userName = "userName";
		String password = "password";

	    // when
		when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.empty());
		when(userEntityRepository.save(any())).thenReturn(Optional.of(UserEntityFixture.get(userName, password)));

	    // then
		Assertions.assertDoesNotThrow(()->userService.join(userName,password));
	}

	@Test
	@DisplayName("회원가입 실패 - 이미 있는 유저")
	void join_failure() throws Exception{

		// given
		String userName = "userName";
		String password = "password";
		UserEntity fixture = UserEntityFixture.get(userName, password);

		// when
		when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(fixture));
		when(userEntityRepository.save(any())).thenReturn(Optional.of(UserEntityFixture.get(userName, password)));

		// then
		Assertions.assertThrows(SnsApplicationException.class, ()->userService.join(userName,password));
	}


	@Test
	@DisplayName("로그인 정상 동장")
	void login_success() throws Exception{

		// given
		String userName = "userName";
		String password = "password";
		UserEntity fixture = UserEntityFixture.get(userName, password);

		// when
		when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(fixture));

		// then
		Assertions.assertDoesNotThrow(()->userService.login(userName,password));
	}

	@Test
	@DisplayName("로그인 실패 - 없는 유저")
	void login_failure() throws Exception{

		// given
		String userName = "userName";
		String password = "password";
		UserEntity fixture = UserEntityFixture.get(userName, password);


		// when
		when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.empty());

		// then
		Assertions.assertThrows(SnsApplicationException.class, ()->userService.login(userName,password));
	}

	@Test
	@DisplayName("로그인 실패 - 패스워드가 틀린 경우")
	void login_failure2() throws Exception{

		// given
		String userName = "userName";
		String password = "password";
		String wrongPassword = "wrongPassword";

		UserEntity fixture = UserEntityFixture.get(userName, password);


		// when
		when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(fixture));

		// then
		Assertions.assertThrows(SnsApplicationException.class, ()->userService.login(userName,password));
	}

}
