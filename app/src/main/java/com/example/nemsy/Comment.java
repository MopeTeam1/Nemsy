package com.example.nemsy;

public class Comment {
    String nickname;
    String datetime;
    String content;

    public Comment(String nickname, String datetime, String content) {
        this.nickname = nickname;
        this.datetime = datetime;
        this.content = content;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
