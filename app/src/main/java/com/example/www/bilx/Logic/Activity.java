package com.example.www.bilx.Logic;

import android.graphics.Color;

import com.alamkanak.myweekview.WeekViewEvent;

import java.util.Calendar;

public class Activity {
    private String title, club, time, date,location;
    private int gePoints, language;
    private WeekViewEvent wve;

    public Activity(String title, String club, String time, String date, String location, int gePoints, int language) {
        this.title = title;
        this.club = club;
        this.gePoints = gePoints;
        this.time = time;
        this.date = date;
        this.location = location;
        this.language = language;

        Calendar startTime = Calendar.getInstance();
        startTime.set( Integer.parseInt(date.substring(6, 10)), Integer.parseInt(date.substring(3, 5)) - 1,
               Integer.parseInt(date.substring(0,2)), Integer.parseInt(time.substring(0,2)), 0);
        Calendar endTime = (Calendar)startTime.clone();
        endTime.add(Calendar.HOUR_OF_DAY, 1);
        //endTime.set(Calendar.MONTH, 4);

//        Calendar startTime = Calendar.getInstance();
//        startTime.set(Calendar.HOUR_OF_DAY, 3);
//        startTime.set(Calendar.MINUTE, 0);
//        startTime.set(Calendar.MONTH, 4);
//        startTime.set(Calendar.YEAR, 2018);
//        Calendar endTime = (Calendar) startTime.clone();
//        endTime.add(Calendar.HOUR, 1);
//        endTime.set(Calendar.MONTH, 4);

        wve = new WeekViewEvent( 1, this.title, startTime, endTime );
        wve.setColor( Color.parseColor("#01579B"));

    }

    public WeekViewEvent getWve(){
        return this.wve;
    }

    public String getClub() {
        return club;
    }

    public int getGePoints() {
        return gePoints;
    }

    public String getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }

    public String getTitle() {
        return title;
    }

    public int getLanguage(){
        return language;
    }

    public String getLocationString(){
       return location;
    }

    public String getLanguageString(){
        if ( getLanguage() == 1)
            return "English";
        return "Turkish";
    }

}
