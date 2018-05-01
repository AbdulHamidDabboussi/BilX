package com.example.www.bilx.Fragments.User;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.www.bilx.R;

/**
 *  The settings fragment for the admin class.
 *  @author Hanzallah Burney
 */
public class UserActivities extends android.support.v4.app.Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view =  inflater.inflate(R.layout.user_activities, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.user__account,menu );
        super.onCreateOptionsMenu(menu, inflater);
    }

}
