package com.example.fastsnspractice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

import com.example.fastsnspractice.model.entity.CommentEntity;

@Getter
@AllArgsConstructor
public class Comment {
    private Integer id;
    private String comment;
    private Integer userId;
    private String userName;
    private Integer postId;
    private Timestamp registeredAt;
    private Timestamp updatedAt;
    private Timestamp removedAt;

    public static Comment fromEntity(CommentEntity entity) {
        return new Comment(
                entity.getId(),
                entity.getComment(),
                entity.getUser().getId(),
                entity.getUser().getUserName(),
                entity.getPost().getId(),
                entity.getRegisteredAt(),
                entity.getUpdatedAt(),
                entity.getRemovedAt()
        );
    }
}
