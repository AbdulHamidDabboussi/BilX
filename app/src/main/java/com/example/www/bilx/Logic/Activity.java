package com.example.www.bilx.Logic;

import java.util.Calendar;

/**
 * Activity class
 * @author Gledis & Perman
 * @version 20/04/2018
 */

public class Activity
{
    // constants
    final static int CONFERENCE = 0;
    final static int SCREENING = 1;
    final static int SEMINAR = 2;
    final static int SOCIAL = 3;
    final static int SPORTS = 4;
    final static int TRIP = 5;
    final static int WORKSHOP = 6;
    final static int OTHER = 7;

    final static int BOTH = 0;
    final static int TUR = 1;
    final static int ENG = 2;

    final static int STATUS_PENDING = 0;
    final static int STATUS_APPROVED = 1;
    final static int STATUS_REJECTED = 2;

    // properties
    String name;
    String club;
    String location;
    String description;

    int lang;
    int category;
    int gePoints;
    int pt;           // Participation Tracker
    int status;       // Whether the activity is active,rejected, or awating confirmation.

    Calendar time;

    boolean onCampus;
    boolean saved;
    boolean limited;
    boolean expired;
    boolean isNew;

    // Alarm

    // constructor
    public Activity()
    {
        pt = 0;
        saved = false;
        expired = false;
        isNew = true;
        status = STATUS_PENDING;
    }

    // methods
    // print... type methods diplay data in a user friendly way

    // prints name of activity
    public String printName(){ return name;}

    // prints location
    public String printLocation(){ return location;}

    // prints description
    public String printDescription(){ return description;}

    // prints GE points
    public String printGEPoints(){ return "" + gePoints;}

    // prints the Participation Tracker of the activity
    public String printPT(){ return "" + pt;}

    // prints the hours and minutes only
    public String printTime()
    {
        String hour = "" + time.get( Calendar.HOUR_OF_DAY);
        String minute = "" + time.get( Calendar.MINUTE);

        // making sure the format is hh:mm
        if( hour.length() == 1)
            hour = "0" + hour;
        if( minute.length() == 1)
            minute = "0" + minute;

        return  hour + ":" + minute;
    }

    // prints the day,month, and year
    public String printDate()
    {
        String day = "" + time.get( Calendar.DAY_OF_MONTH);
        String month = "" + time.get( Calendar.MONTH);
        String year = "" + time.get( Calendar.YEAR);

        // making sure the format is dd/mm/yyyy
        if( day.length() == 1)
            day = "0" + day;
        if( month.length() == 1)
            month = "0" + month;

        return day + "/" + month + "/" + year;
    }

    // prints the language
    public String printLang()
    {
        if( lang == BOTH)
            return "Both";
        else if( lang == TUR)
            return "Tur";
        else
            return "Eng";
    }

    // prints the category
    public String printCategory()
    {
        if( category == CONFERENCE)
            return "Conference";
        else if( category == SCREENING)
            return "Screening";
        else if( category == SEMINAR)
            return "Seminar";
        else if( category == SOCIAL)
            return "Social";
        else if( category == SPORTS)
            return "Sport";
        else if( category == TRIP)
            return "Trip";
        else if( category == WORKSHOP)
            return "Workshop";
        else
            return "Other";
    }

    // prints whether the activity is happening on campus or not
    public String printOnCampusStatus()
    {
        if( onCampus)
            return "On Campus";
        else
            return "Off Campus";
    }
}