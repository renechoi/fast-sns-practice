package com.example.fastsnspractice.service;

import com.example.fastsnspractice.exception.ErrorCode;
import com.example.fastsnspractice.exception.SimpleSnsApplicationException;
import com.example.fastsnspractice.model.Alarm;
import com.example.fastsnspractice.model.User;
import com.example.fastsnspractice.model.entity.UserEntity;
import com.example.fastsnspractice.repository.AlarmEntityRepository;
import com.example.fastsnspractice.repository.UserCacheRepository;
import com.example.fastsnspractice.repository.UserEntityRepository;
import com.example.fastsnspractice.utils.JwtTokenUtils;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserEntityRepository userRepository;
	private final AlarmEntityRepository alarmEntityRepository;
	private final BCryptPasswordEncoder encoder;
	private final UserCacheRepository redisRepository;



	@Value("${jwt.secret-key}")
	private String secretKey;

	@Value("${jwt.token.expired-time-ms}")
	private Long expiredTimeMs;


	public User loadUserByUsername(String userName) throws UsernameNotFoundException {
		return redisRepository.getUser(userName).orElseGet(
			() -> userRepository.findByUserName(userName).map(User::fromEntity).orElseThrow(
				() -> new SimpleSnsApplicationException(
					ErrorCode.USER_NOT_FOUND, String.format("userName is %s", userName))
			));
	}

	public String login(String userName, String password) {
		User savedUser = loadUserByUsername(userName);
		redisRepository.setUser(savedUser);
		if (!encoder.matches(password, savedUser.getPassword())) {
			throw new SimpleSnsApplicationException(ErrorCode.INVALID_PASSWORD);
		}
		return JwtTokenUtils.generateAccessToken(userName, secretKey, expiredTimeMs);
	}


	@Transactional
	public User join(String userName, String password) {
		// check the userId not exist
		userRepository.findByUserName(userName).ifPresent(it -> {
			throw new SimpleSnsApplicationException(ErrorCode.DUPLICATED_USER_NAME, String.format("userName is %s", userName));
		});

		UserEntity savedUser = userRepository.save(UserEntity.of(userName, encoder.encode(password)));
		return User.fromEntity(savedUser);
	}

	@Transactional
	public Page<Alarm> alarmList(Integer userId, Pageable pageable) {
		return alarmEntityRepository.findAllByUserId(userId, pageable).map(Alarm::fromEntity);
	}

}
