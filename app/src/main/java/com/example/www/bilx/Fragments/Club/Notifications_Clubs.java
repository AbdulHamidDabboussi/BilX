package com.example.www.bilx.Fragments.Club;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.www.bilx.Fragments.Admin.ApproveActivitiesObject;
import com.example.www.bilx.Fragments.User.NotificationsAdapter;
import com.example.www.bilx.Fragments.User.UserNotificationObject;
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

public class Notifications_Clubs extends android.support.v4.app.Fragment {

    public static List<ClubNotificationObject> clubNotifyList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ClubNotificationsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.club_notifications, container, false);

        adapter = new ClubNotificationsAdapter(clubNotifyList);
        recyclerView = (RecyclerView) view.findViewById(R.id.club_recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        clubNotifyList = new ArrayList<>();
        Timer timer = new Timer();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        itemTouchHelper.attachToRecyclerView(recyclerView);


        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                            .child("Notification List").child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                    Query q = databaseReference.orderByChild("Date");

                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot shot) {
//                            ClubNotificationsAdapter.clubArray = new ArrayList<>();
                            clubNotifyList = new ArrayList<>();
                            for (DataSnapshot ds : shot.getChildren()) {
                                String s = ds.getValue().toString();
                                s = s.replace("_",".");
                                String val = s.substring(s.indexOf(',') + 1, s.lastIndexOf('=')).trim();
                                if (!val.equals("Date")) {
                                    addItem(new ClubNotificationObject("Administrator Notification", val, ""));
                                } else {
                                    val = s.substring(s.indexOf('{') + 1, s.indexOf('=')).trim();
                                    addItem(new ClubNotificationObject("Administrator Notification", val, ""));
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                } catch (NullPointerException e) {
                    // do something
                }

            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 2 * 1000);

        return view;
    }

    public void addItem(ClubNotificationObject newItem) {
        clubNotifyList.add(0, newItem);
        adapter.notifyDataSetChanged();
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
                ClubNotificationObject clubNotificationObject = clubNotifyList.get(viewHolder.getAdapterPosition());
                String subject = clubNotificationObject.getSubject();
                subject = subject.replace(".","_");
                clubNotifyList.remove(viewHolder.getAdapterPosition());
                adapter.removeAdapter(viewHolder.getAdapterPosition());
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Notification List")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName()).child(subject);
                reference.removeValue();
                Snackbar.make(getActivity().findViewById(R.id.club_notificationsLayout), "Notification Deleted", Snackbar.LENGTH_LONG).show();

            }
        };
        return simpleCallback;
    }


}
