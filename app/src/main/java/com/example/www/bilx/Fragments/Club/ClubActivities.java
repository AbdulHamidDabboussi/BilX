package com.example.www.bilx.Fragments.Club;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.www.bilx.R;

/**
 *  The settings fragment for the admin class.
 *  @author Hanzallah Burney
 */

public class ClubActivities extends android.support.v4.app.Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.club_activities, container, false);
    }
}
