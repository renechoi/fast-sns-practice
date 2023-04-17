package com.example.fastsnspractice.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class UserEntity {
	@Id
	private Integer id;

	private String userName;

	private String password;
}
