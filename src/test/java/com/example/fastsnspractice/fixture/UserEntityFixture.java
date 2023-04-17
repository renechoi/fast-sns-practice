package com.example.fastsnspractice.fixture;

import java.sql.Timestamp;
import java.time.Instant;

import com.example.fastsnspractice.model.entity.UserEntity;

public class UserEntityFixture {

	public static UserEntity get(String userName, String password) {
		UserEntity entity = new UserEntity();
		entity.setId(1);
		entity.setUserName(userName);
		entity.setPassword(password);
		return entity;
	}
}
