package com.example.fastsnspractice.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.fastsnspractice.controller.repository.UserEntityRepository;
import com.example.fastsnspractice.exception.SnsApplicationException;
import com.example.fastsnspractice.model.User;
import com.example.fastsnspractice.model.entity.UserEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

	private final UserEntityRepository userEntityRepository;

	public User join(String userName, String password){

		Optional<UserEntity> userEntity = userEntityRepository.findByUserName(userName);

		userEntityRepository.save(new UserEntity());

		return new User();
	}

	public String login(String userName, String password){

		UserEntity userEntity = userEntityRepository.findByUserName(userName)
			.orElseThrow(SnsApplicationException::new);

		if (!userEntity.getPassword().equals(password)){
			throw new SnsApplicationException();
		}

		return "";
	}
}
