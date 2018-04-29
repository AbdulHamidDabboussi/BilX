package com.example.www.bilx.Fragments.User;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.www.bilx.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 *  The settings fragment for the admin class.
 *  @author Hanzallah Burney
 */

public class Notifications_User extends android.support.v4.app.Fragment {
    private List<UserNotificationObject> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private NotificationsAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
       View view = inflater.inflate(R.layout.notifications_user, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        mAdapter = new NotificationsAdapter(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference().child("Notifications")
                .child("Users").child("Message");


        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue().toString().substring(dataSnapshot.getValue().toString().indexOf("=")+1
                ,dataSnapshot.getValue().toString().indexOf("}"));
                addItem(new UserNotificationObject("0","1","2"));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }

    public void addItem(final UserNotificationObject newItem) {
        movieList.add(newItem);
        mAdapter.notifyDataSetChanged();

    }

}
