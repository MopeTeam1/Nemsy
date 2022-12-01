package com.example.nemsy;

public class Comment {
    Long id;
    String content;
    String userId;
    String userNickname;
    String billId;
    String createdAt;
    String modifiedAt;

    public Comment(Long id, String content, String userId, String userNickname, String billId, String createdAt, String modifiedAt) {
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.userNickname = userNickname;
        this.billId = billId;
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

    public String getBillId() {
        return billId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getModifiedAt() {
        return modifiedAt;
    }
}
