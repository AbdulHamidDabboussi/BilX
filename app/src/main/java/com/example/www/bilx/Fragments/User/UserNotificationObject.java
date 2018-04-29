package com.example.www.bilx.Fragments.User;

public class UserNotificationObject {
    private String sentBy, subject, content;

    public UserNotificationObject(String sentBy, String subject, String content) {
        this.sentBy = sentBy;
        this.subject = subject;
        this.content = content;
    }

    public String getSentBy() {
        return sentBy;
    }

    public void setSentBy(String sentBy) {
        this.sentBy = sentBy;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
