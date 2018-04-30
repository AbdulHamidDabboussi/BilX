package com.example.www.bilx.Fragments.User;

import android.media.Image;
import android.widget.ImageView;

public class ClubListObject {
    private String sentBy, subject;
    private String uri;

    public ClubListObject(String sentBy, String subject, String uri) {
        this.sentBy = sentBy;
        this.subject = subject;
        this.uri = uri;
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

    public String getClubIcon() {
        return uri;
    }

    public void setClubIcon(String content) {
        this.uri = uri;
    }
}
