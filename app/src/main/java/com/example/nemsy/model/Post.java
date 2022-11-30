package com.example.nemsy.model;

public class Post {
    String title;
    String content;
    String author;
    String createdAt;
    int likeCount;

    public Post(String title, String content, String author, String createdAt, int likeCount){
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
        this.likeCount = likeCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }
}
