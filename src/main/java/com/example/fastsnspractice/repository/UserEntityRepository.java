package com.example.fastsnspractice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.fastsnspractice.model.entity.UserEntity;

public interface UserEntityRepository extends JpaRepository<UserEntity, Integer> {

	Optional<UserEntity> findByUserName(String userName);
}
