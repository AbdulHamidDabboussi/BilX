package com.example.www.bilx.Logic;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * ActivityList class
 * @author Gledis & Perman
 * @version 20/04/2018
 */

public class ActivityList
{
    // properties
    ArrayList<Activity> list;

    // constructor
    public ActivityList()
    {
        list = new ArrayList<>();
    }

    // methods

    /**
     * add a new activity to the list
     * @param Activity the new activity to be added
     */
    public void add( Activity newActivity)
    {
        list.add( newActivity);
    }

    /**
     * Makes all the activities in the list not new anymore
     */
    public void seen()
    {
        Iterator<Activity> it = iterator();

        while( it.hasNext())
        {
            /* TODO Check Logic Again: DO WE NEED USERACCESS CLASS B/C ANDROID OPERATES ON A DIFFERENT LOGIC THAN TRADITIONAL JAVA */
         //  UserAccess temp = new UserAccess( it.next());
           // temp.unNew();
        }
    }

    public void cleanUp()
    {
        // deal with activities which are supposed to expire and with the rejected
        // activities which haven't been addressed by the respective clubs
    }

    /**
     * provides an iterator to navigate through the list with ease
     * @return Iterator<Activity> to iterate through activities in the list
     */
    public Iterator<Activity> iterator()
    {
        return list.iterator();
    }
}