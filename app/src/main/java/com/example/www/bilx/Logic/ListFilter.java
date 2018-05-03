
package com.example.www.bilx.Logic;
import com.example.www.bilx.Accounts.User_Account;
import com.example.www.bilx.Fragments.User.UserActivities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ListFilter {

//    // user filters
//    public static final int DEFAULT_FILTER = 0;
//    public static final int GE_FILTER = 1;
//    public static final int LANGUAGE_FILTER = 2;
//    public static final int ON_OFF_CAMPUS_FILTER = 3;
//
//
//    private ArrayList<Activity> filteredActivityList;
//    private ArrayList<Activity> unFilteredActivityList = UserActivities.activityList;
//
//    public ListFilter(int filterType){
//        if ( filterType == DEFAULT_FILTER) { defaultFilter(); }
//        if ( filterType == GE_FILTER) { geFilter();}
//        if ( filterType == LANGUAGE_FILTER) { languageFilter(); }
//        if ( filterType == ON_OFF_CAMPUS_FILTER) { locationFilter(); }
//    }
//
//
//    //Filtering Methods based on type of filter
//    private void defaultFilter(){
//        filteredActivityList = unFilteredActivityList;
//    }
//
//    private void geFilter(){
//        Collections.sort(filteredActivityList, new Comparator<Activity>() {
//            @Override
//            public int compare(Activity activity1, Activity activity2) {
//                return activity1.getGePoints() - activity2.getGePoints();
//            }
//        });
//    }
//
//    private void languageFilter(){
//        Collections.sort(filteredActivityList, new Comparator<Activity>() {
//            @Override
//            public int compare(Activity activity1, Activity activity2) {
//                return activity2.getLanguage() - activity1.getLanguage();
//            }
//        });
//    }
//
//    private void locationFilter(){
//        Collections.sort(filteredActivityList, new Comparator<Activity>() {
//            @Override
//            public int compare(Activity activity1, Activity activity2) {
//                return activity2.getLocation() - activity1.getLocation();
//            }
//        });
//    }
//
//    public ArrayList<Activity> getFilteredActivityList() {
//        return filteredActivityList;
//    }
}
