package com.example.www.bilx.Fragments.Information.User_Info_FAQs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.www.bilx.R;

public class User_Club_FAQ extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Club FAQs");
        return inflater.inflate(R.layout.user_club_faq, container, false);
    }
}
