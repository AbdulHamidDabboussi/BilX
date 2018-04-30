package com.example.www.bilx.Fragments.User;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.www.bilx.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 *  The settings fragment for the admin class.
 *  @author Hanzallah Burney
 */

public class Notifications_User extends android.support.v4.app.Fragment {
    public static  List<UserNotificationObject> notifyList = new ArrayList<>();
    private RecyclerView recyclerView;
    private NotificationsAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
       final View view = inflater.inflate(R.layout.notifications_user, container, false);


        mAdapter = new NotificationsAdapter(notifyList);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        notifyList = new ArrayList<>();
        Timer timer = new Timer();


        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                     DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                            .child("Notification List").child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                    Query q = databaseReference.orderByChild("Date");


                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange( DataSnapshot shot) {
                            notifyList = new ArrayList<>();
                            for( DataSnapshot ds: shot.getChildren()){
                                String s = ds.getValue().toString();
                                String val = s.substring(s.indexOf(',')+1,s.lastIndexOf('=')).trim();
                                if (!val.equals("Date")){
                                    addItem(new UserNotificationObject("Administrator Notification",val,""));
                                }
                                else{
                                    val = s.substring(s.indexOf('{')+1,s.indexOf('=')).trim();
                                    addItem(new UserNotificationObject("Administrator Notification",val,""));
                                }
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                } catch (NullPointerException e){
                    // do something
                }

            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 2*1000);


        return view;
    }

    public void addItem(UserNotificationObject newItem) {
        notifyList.add(0,newItem);
        mAdapter.notifyDataSetChanged();

    }

}
