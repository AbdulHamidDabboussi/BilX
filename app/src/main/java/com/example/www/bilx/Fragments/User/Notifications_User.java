package com.example.www.bilx.Fragments.User;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.www.bilx.Accounts.User_Account;
import com.example.www.bilx.Fragments.Club.ClubNotificationObject;
import com.example.www.bilx.Fragments.Club.Notifications_Clubs;
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

public class Notifications_User extends android.support.v4.app.Fragment implements SwipeRefreshLayout.OnRefreshListener {
    public static  List<UserNotificationObject> notifyList = new ArrayList<>();
    private RecyclerView recyclerView;
    public  NotificationsAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
       final View view = inflater.inflate(R.layout.notifications_user, container, false);
       final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());

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
                                s = s.replace("_",".");
                                String val = s.substring(s.indexOf(',')+1,s.lastIndexOf('=')).trim();

                                mAdapter = new NotificationsAdapter(notifyList);
                                recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

                                recyclerView.setLayoutManager(mLayoutManager);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setAdapter(mAdapter);

                                if (!val.equals("Date")){
                                    addItem(new UserNotificationObject("Administrator Notification",val,""));
                                }
                                else{
                                    val = s.substring(s.indexOf('{')+1,s.indexOf('=')).trim();
                                    addItem(new UserNotificationObject("Administrator Notification",val,""));
                                }
                                ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
                                itemTouchHelper.attachToRecyclerView(recyclerView);
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

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refreshUser);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                        new Notifications_User()).commit();
            }
        });
        return view;
    }

    public void addItem(UserNotificationObject newItem) {
        notifyList.add(0,newItem);
        mAdapter.notifyDataSetChanged();

    }

    private ItemTouchHelper.Callback createHelperCallback(){
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN
                ,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                try {
                    UserNotificationObject clubNotificationObject = notifyList.get(viewHolder.getAdapterPosition());
                    String subject = clubNotificationObject.getSubject();
                    subject = subject.replace(".","_");
//                notifyList.remove(viewHolder.getAdapterPosition());
//                mAdapter.removeAdapter(viewHolder.getAdapterPosition());
//                mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Notification List")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName()).child(subject);
                    reference.removeValue();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                            new Notifications_User()).commit();
                    Snackbar.make(getActivity().findViewById(R.id.user_notificationsLayout), "Notification Deleted", Snackbar.LENGTH_LONG).show();
                }catch (Exception e){

                }
            }
        };
        return simpleCallback;
    }

    @Override
    public void onRefresh(){
        // Empty
    }
}
