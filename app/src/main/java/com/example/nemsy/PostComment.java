package com.example.nemsy;

public class PostComment {
    Long id;
    String content;
    String userId;
    String userNickname;
    Long postId;
    String createdAt;
    String modifiedAt;

    public PostComment(Long id, String content, String userId, String userNickname, Long postId, String createdAt, String modifiedAt) {
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.userNickname = userNickname;
        this.postId = postId;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public Long getPostId() {
        return postId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getModifiedAt() {
        return modifiedAt;
    }
}
