package com.example.www.bilx.Fragments.Admin;

public class ApproveActivitiesObject {
    private String activityName,clubName, ge, time, date, location, language, description;

    public ApproveActivitiesObject(String activityName, String clubName, String ge, String time, String date, String location, String language, String description) {
        this.activityName = activityName;
        this.clubName = clubName;
        this.ge = ge;
        this.time = time;
        this.date = date;
        this.location = location;
        this.language = language;
        this.description = description;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getGe() {
        return ge;
    }

    public void setGe(String ge) {
        this.ge = ge;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
