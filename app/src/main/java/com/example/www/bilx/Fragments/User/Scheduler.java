package com.example.www.bilx.Fragments.User;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alamkanak.myweekview.MonthLoader;
import com.alamkanak.myweekview.WeekView;
import com.alamkanak.myweekview.WeekViewEvent;
import com.alamkanak.myweekview.WeekViewLoader;

import com.example.www.bilx.Logic.Activity;
import com.example.www.bilx.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *  The settings fragment for the admin class.
 *  @author Hanzallah Burney
 */

public class Scheduler extends android.support.v4.app.Fragment {

    //variables
    final ArrayList<WeekViewEvent> events = new ArrayList<>();
    WeekView mWeekView;
    View view;






    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view = inflater.inflate(R.layout.smart_scheduler, container, false);

        final Activity test = new Activity("title", "club", "15:20", "01/05/2018", "1", 50, 1);
        events.add( test.getWve());


        // Get a reference for the week view in the layout.
        mWeekView = (WeekView) view.findViewById(R.id.weekView);

        //MonthChangeListener
        mWeekView.setMonthChangeListener( new MonthLoader.MonthChangeListener(){
            @Override
            public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
                //events.add( test.getWve());
                return events;
            }
        });

        mWeekView.setWeekViewLoader(new WeekViewLoader() {
            //this variable is used to count the execution time of onLoad() function
            int i=1;
            @Override
            public double toWeekViewPeriodIndex(Calendar instance) {

                //ignore this line
                return instance.getTime().getDate()+instance.getTime().getDate();
            }

            @Override
            public List<WeekViewEvent> onLoad(int periodIndex) {
                //if onLoad function execution count is 3 then load events and set i=0 for next time view Load
                //else  onLoad function execute 1st and 2nd time load only empty event list
                if(i==3)
                {

                    i=1;
                    //this is custom function to load list of WeeViewkEvents
                    return events;
                }
                else
                {
                    i++;
                    //empty List
                    List events=new ArrayList();
                    return events;
                }
            }
        });


        if (mWeekView.isReleased){
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new UserActivities()).commit();
            Log.i("tag", "isreleased");
            mWeekView.isReleased = false;
        }

        return view;
    }
}
