package com.example.www.bilx.Fragments.User;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.www.bilx.Accounts.User_Account;
import com.example.www.bilx.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 *  The settings fragment for the admin class.
 *  @author Hanzallah Burney
 */

public class UserClubs extends android.support.v4.app.Fragment {
    private List<ClubListObject> clubList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ClubListAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.user_clubs, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.club_list_recycler_view);

        mAdapter = new ClubListAdapter(clubList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference().child("Users");

        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    if ( dataSnapshot1.getValue().toString().contains("club")){
                       String s = dataSnapshot1.toString();
                       final String val = s.substring(s.indexOf('=')+1,s.indexOf(',')).trim();

                       //=====================================================================
                        DatabaseReference profileRef = FirebaseDatabase.getInstance().getReference()
                                .child("Club profiles").child(val);
                        Query query = profileRef.orderByPriority();
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                try {
                                    final String prof = dataSnapshot.getValue().toString();
                                    final String profile = prof.substring(prof.indexOf('=') + 1, prof.indexOf('}'));
                                    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                                    StorageReference storageRef = firebaseStorage.getReference();
                                    storageRef.child(val + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            addItem(new ClubListObject(val,
                                                    profile, uri.toString()));
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            // Handle any errors
                                        }
                                    });
                                } catch (Exception e){

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        //======================================================================
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        return view;
    }

    public void addItem(final ClubListObject newItem) {
        clubList.add(0,newItem);
        mAdapter.notifyDataSetChanged();

    }

}

