package com.example.fastsnspractice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.fastsnspractice.repository.UserEntityRepository;
import com.example.fastsnspractice.exception.ErrorCode;
import com.example.fastsnspractice.exception.SnsApplicationException;
import com.example.fastsnspractice.model.User;
import com.example.fastsnspractice.model.entity.UserEntity;
import com.example.fastsnspractice.utils.JwtTokenUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

	private final UserEntityRepository userRepository;
	private final BCryptPasswordEncoder encoder;



	@Value("${jwt.secret-key}")
	private String secretKey;

	@Value("${jwt.token.expired-time-ms}")
	private Long expiredTimeMs;


	// public User loadUserByUsername(String userName) throws UsernameNotFoundException {
	// 	return redisRepository.getUser(userName).orElseGet(
	// 		() -> userRepository.findByUserName(userName).map(User::fromEntity).orElseThrow(
	// 			() -> new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("userName is %s", userName))
	// 		));
	// }

	public String login(String userName, String password) {
		UserEntity savedUser = userRepository.findByUserName(userName)
			.orElseThrow(() -> new UsernameNotFoundException("not found"));

		// User savedUser = loadUserByUsername(userName);
		// redisRepository.setUser(savedUser);
		if (!encoder.matches(password, savedUser.getPassword())) {
			throw new SnsApplicationException(ErrorCode.INVALID_PASSWORD);
		}
		return JwtTokenUtils.generateAccessToken(userName, secretKey, expiredTimeMs);
	}


	@Transactional
	public User join(String userName, String password) {
		// check the userId not exist
		userRepository.findByUserName(userName).ifPresent(it -> {
			throw new SnsApplicationException(
				ErrorCode.DUPLICATED_USER_NAME, String.format("userName is %s", userName));
		});

		UserEntity savedUser = userRepository.save(UserEntity.of(userName, encoder.encode(password)));
		return User.fromEntity(savedUser);
	}

}
