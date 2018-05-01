package com.example.www.bilx.Fragments.Club;

public class ClubActivitiesObject {
    private String activityName, ge, time, date, location, language, description,status;

    public ClubActivitiesObject(String activityName, String ge, String time, String date, String location, String language, String description, String status) {
        this.activityName = activityName;
        this.ge = ge;
        this.time = time;
        this.date = date;
        this.location = location;
        this.language = language;
        this.description = description;
        this.status = status;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
