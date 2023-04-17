package com.example.fastsnspractice.model.entity;

import java.sql.Timestamp;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.example.fastsnspractice.model.UserRole;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Table(name = "\"user\"")
@Entity
@SQLDelete(sql = "UPDATE \"user\" SET removed_at = NOW() WHERE id=?")
@Where(clause = "removed_at is NULL")
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id = null;

	@Column(name = "user_name", unique = true)
	private String userName;

	private String password;

	@Enumerated(EnumType.STRING)
	private UserRole role = UserRole.USER;

	@Column(name = "registered_at")
	private Timestamp registeredAt;

	@Column(name = "updated_at")
	private Timestamp updatedAt;

	@Column(name = "removed_at")
	private Timestamp removedAt;


	@PrePersist
	void registeredAt() {
		this.registeredAt = Timestamp.from(Instant.now());
	}

	@PreUpdate
	void updatedAt() {
		this.updatedAt = Timestamp.from(Instant.now());
	}


	public static UserEntity of(String userName, String encodedPwd) {
		UserEntity entity = new UserEntity();
		entity.setUserName(userName);
		entity.setPassword(encodedPwd);
		return entity;
	}
}
