package com.example.www.bilx.Logic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.Calendar;

/**
 * ListFilter
 * @author Gledis & Perman
 * @version 20/04/2018
 */
//TODO CHECK YOUR LOGIC WITH RESPECT TO ANDROID NOT TRADITIONAL JAVA
public class ListFilter extends ActivityList {

    // constants

    // user filters
    static final int DEFAULT_FILTER = 0;
    static final int PARTICIPATION_TRACKER_FILTER = 1;
    static final int GE_FILTER = 2;
    static final int LANG_FILTER = 3;
    static final int ON_OFF_CAMPUS_FILTER = 4;
    static final int CATEGORY_FILTER = 5;
    static final int SAVED_FILTER = 6;
    static final int CLUB_ACTIVITIES_FILTER = 7;

    // club filter
    static final int CLUB_OWN_ACTIVITIES_FILTER = 8;

    // admin filter
    static final int PENDING_FILTER = 9;

    // constructors

    /**
     * normal filters constructor
     * @param ActivityList the universal activity list where all existing activities are held
     * @param final int the type of filering to be used
     */
    public ListFilter( ArrayList<Activity> universalList, final int filterType)
    {
        // if the filter is for user access
        if( filterType != PENDING_FILTER)
        {
            // discard all the non approved activites
            for( int i = 0; i < universalList.size(); i++)
                if( universalList.get( i).status == Activity.STATUS_APPROVED)
                    list.add( universalList.get( i));

            // call the right filter to be used
            if( filterType == DEFAULT_FILTER)
            {
                defaultFilter();
            }
            else if( filterType == PARTICIPATION_TRACKER_FILTER)
            {
                ptFilter();
            }
            else if( filterType == GE_FILTER)
            {
                geFilter();
            }
            else if( filterType == LANG_FILTER)
            {
                langFilter();
            }
            else if( filterType == ON_OFF_CAMPUS_FILTER)
            {
                onOffFilter();
            }
            else if( filterType == CATEGORY_FILTER)
            {
                categoryFilter();
            }
            else if( filterType == SAVED_FILTER)
            {
                savedFilter();
            }
        }
        else if( filterType == PENDING_FILTER)
        {
            // discard all the non approved activities
            for( int i = 0; i < universalList.size(); i++)
                if( universalList.get( i).status != Activity.STATUS_APPROVED)
                    list.add( universalList.get( i));
            pendingFilter();
        }
        else
        {
            // print error message...
        }
    }

    /**
     * time spawn filtering constructor
     * @param ActivityList the universal activity list where all existing activities are held
     * @param int the hour of the minimum starting time
     * @param int the minutes of the minimum starting time
     * @param int the hour of the maximum starting time
     * @param int the minutes of the maximum starting time
     * @param int the day of the activity
     * @param the month of the activity
     * @param the year of the activity
     */
    public ListFilter( ArrayList<Activity> universalList, int sHour, int sMin, int eHour, int eMin, int day, int month,
                       int year)
    {
        // discard all the non approved activites
        for( int i = 0; i < universalList.size(); i++)
            if( universalList.get( i).status == Activity.STATUS_APPROVED)
                list.add( universalList.get( i));

        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();

        start.set( year, month, day, sHour, sMin);
        end.set( year, month, day, eHour, eMin);

        timeSpanFilter( start, end);
    }

    /**
     * club activity filtering constructor
     * @param ActivityList the universal activity list where all existing activities are held
     * @param final int indicating whether to filter the old or the new activities
     * @param String club name
     */
    public ListFilter( ArrayList<Activity> universalList, final int filterType, String club)
    {

        if( filterType == CLUB_ACTIVITIES_FILTER)
        {
            // discard all the non approved activites
            for( int i = 0; i < universalList.size(); i++)
                if( universalList.get( i).status == Activity.STATUS_APPROVED)
                    list.add( universalList.get( i));

            clubFilter( club);
        }
        else if( filterType == CLUB_OWN_ACTIVITIES_FILTER)
        {
            // perserve the non approved activities
            list = new ArrayList<>( universalList);

            clubFilter( club);
        }
        else
        {
            // print error message...
        }
    }

    // methods

    /**
     * filtering on new, limited, and time parameters
     */
    private void defaultFilter()
    {
        // used to group new limited, new, limited, normal activities
        ArrayList<Activity>[] map;
        map = new ArrayList[4];

        for( int i = 0; i < 4; i++)
            map[i] = new ArrayList<Activity>();

        // grouping activities respectively
        for( int i = 0; i < list.size(); i++)
        {
            Activity temp = list.get( i);

            if( temp.isNew && temp.limited)
                map[0].add( temp);
            else if( temp.isNew)
                map[1].add( temp);
            else if( temp.limited)
                map[2].add( temp);
            else
                map[3].add( temp);
        }

        // sort all 4 groups by time
        for( int i = 0; i < 4; i++)
        {
            Collections.sort( map[0], new Comparator<Activity>(){
                @Override
                public int compare( Activity act1, Activity act2)
                {
                    return act1.time.getTime().compareTo( act2.time.getTime());
                }
            });
        }

        // insert the filtered activities back to the main list
        list.clear();
        for( int i = 0; i < 4; i++)
            for( int j = 0; j < map[i].size(); j++)
                list.add( map[i].get( j));
    }

    /**
     * filtering from highest participation tracker
     */
    private void ptFilter()
    {
        Collections.sort( list, new Comparator<Activity>(){
            @Override
            public int compare( Activity act1, Activity act2)
            {
                return act1.pt - act2.pt;
            }
        });
    }

    /**
     * filering from highest GE points
     */
    public void geFilter()
    {
        Collections.sort( list, new Comparator<Activity>(){
            @Override
            public int compare( Activity act1, Activity act2)
            {
                return act1.gePoints - act2.gePoints;
            }
        });
    }

    /**
     * filering based on languages
     */
    public void langFilter()
    {
        Collections.sort( list, new Comparator<Activity>(){
            @Override
            public int compare( Activity act1, Activity act2)
            {
                return act1.lang - act2.lang;
            }
        });
    }

    /**
     * filering based on whether the activity is in campus or not
     */
    public void onOffFilter()
    {
        Collections.sort( list, new Comparator<Activity>(){
            @Override
            public int compare( Activity act1, Activity act2)
            {
                // prioritising on campus activities
                if( act1.onCampus == act2.onCampus)
                    return 0;
                else if( act1.onCampus)
                    return -1;
                else
                    return 1;
            }
        });
    }

    /**
     * filering based on categories
     */
    public void categoryFilter()
    {
        Collections.sort( list, new Comparator<Activity>(){
            @Override
            public int compare( Activity act1, Activity act2)
            {
                // priority is set in the order of the category final ints in Activity class
                return act1.category - act2.category;
            }
        });
    }

    /**
     * filering based on whether the activity is saved or not
     */
    public void onOffCampusFilter()
    {
        Collections.sort( list, new Comparator<Activity>(){
            @Override
            public int compare( Activity act1, Activity act2)
            {
                // prioritising on campus activities
                if( act1.onCampus == act2.onCampus)
                    return 0;
                else if( act1.onCampus)
                    return -1;
                else
                    return 1;
            }
        });
    }

    /**
     * filtering out the user's saved activities
     */
    public void savedFilter()
    {
        Collections.sort( list, new Comparator<Activity>(){
            @Override
            public int compare( Activity act1, Activity act2)
            {
                // prioritising on campus activities
                if( act1.onCampus != act2.onCampus)
                {
                    if( act1.onCampus)
                        return -1;
                    else
                        return 1;
                }

                // extra layer of filter by time
                return act1.time.getTime().compareTo( act2.time.getTime());
            }
        });
    }

    /**
     * this filtering method works for both user and club display once the specific activities are omitted
     * @param club name
     */
    public void clubFilter( String club)
    {
        ArrayList<Activity> temp;
        temp = new ArrayList<>();

        // select all activities from the specific club
        for( int i = 0; i < list.size(); i++)
            if( list.get( i).club == club)
                temp.add( list.get( i));

        Collections.sort( list, new Comparator<Activity>(){
            @Override
            public int compare( Activity act1, Activity act2)
            {
                // extra layer of filter by time
                return act1.time.getTime().compareTo( act2.time.getTime());
            }
        });
    }

    /**
     * filering based on status
     */
    public void pendingFilter()
    {
        Collections.sort( list, new Comparator<Activity>(){
            @Override
            public int compare( Activity act1, Activity act2)
            {
                return act1.status - act2.status;
            }
        });
    }

    /**
     * Select activities from a certain time interval
     * @param Calendar the minimun starting time
     * @param Calendar the maximum starting time
     */
    public void timeSpanFilter( Calendar s, Calendar e)
    {
        ArrayList<Activity> temp;
        temp = new ArrayList<>();

        // Select activities just in this timespan
        for( int i = 0; i < list.size(); i++)
        {
            if( s.getTimeInMillis() <= list.get( i).time.getTimeInMillis() &&
                    e.getTimeInMillis() >= list.get( i).time.getTimeInMillis())
                temp.add( list.get( i));
        }

        list = temp;

        // give this specific list of activities a default filtering
        defaultFilter();
    }
}