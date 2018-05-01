package com.example.www.bilx.Fragments.Club;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.www.bilx.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CreateNewActivity extends Fragment {
    private EditText actName, ge, time, date, loc,actDesc;
    private Spinner lang;
    private FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_club_activities, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Create New Activity");

        actName = (EditText) view.findViewById(R.id.actName);
        actDesc = (EditText) view.findViewById(R.id.actDescription);
        ge = (EditText) view.findViewById(R.id.gePoints);
        time = (EditText) view.findViewById(R.id.time);
        date = (EditText) view.findViewById(R.id.date);
        loc = (EditText) view.findViewById(R.id.loc);
        lang = (Spinner) view.findViewById(R.id.lang_spinner);
        fab = (FloatingActionButton) view.findViewById(R.id.createAct_fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (actName.getText().toString().equals("")||actDesc.getText().toString().equals("")||ge.getText().toString().equals("")
                        ||time.getText().toString().equals("")||date.getText().toString().equals("")||loc.getText().toString().equals("")){
                    Toast.makeText(getActivity(),"One of the fields is empty", Toast.LENGTH_LONG).show();
                }
                else{
                    String activityName = actName.getText().toString();
                    Map activityValues = new HashMap();
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Club Activities").child(FirebaseAuth.getInstance()
                            .getCurrentUser().getDisplayName()).child(activityName).child("GE");
                    activityValues.put("GE",ge.getText().toString());
                    databaseReference.setValue(activityValues);

                    activityValues = new HashMap();
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Club Activities").child(FirebaseAuth.getInstance()
                            .getCurrentUser().getDisplayName()).child(activityName).child("Time");
                    activityValues.put("Time",time.getText().toString());
                    databaseReference.setValue(activityValues);



                    activityValues = new HashMap();
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Club Activities").child(FirebaseAuth.getInstance()
                            .getCurrentUser().getDisplayName()).child(activityName).child("Date");
                    activityValues.put("Date",date.getText().toString());
                    databaseReference.setValue(activityValues);


                    activityValues = new HashMap();
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Club Activities").child(FirebaseAuth.getInstance()
                            .getCurrentUser().getDisplayName()).child(activityName).child("Location");
                    activityValues.put("Location",loc.getText().toString());
                    databaseReference.setValue(activityValues);

                    if (lang.getSelectedItem().toString().equals("English")){
                        activityValues = new HashMap();
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Club Activities").child(FirebaseAuth.getInstance()
                                .getCurrentUser().getDisplayName()).child(activityName).child("Language");
                        activityValues.put("Language","English");
                        databaseReference.setValue(activityValues);

                    }
                    else{
                        activityValues = new HashMap();
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Club Activities").child(FirebaseAuth.getInstance()
                                .getCurrentUser().getDisplayName()).child(activityName).child("Language");
                        activityValues.put("Language","Turkish");
                        databaseReference.setValue(activityValues);

                    }


                    activityValues = new HashMap();
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Club Activities").child(FirebaseAuth.getInstance()
                            .getCurrentUser().getDisplayName()).child(activityName).child("Description");
                    activityValues.put("Description",actDesc.getText().toString());
                    databaseReference.setValue(activityValues);


                    activityValues = new HashMap();
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Club Activities").child(FirebaseAuth.getInstance()
                            .getCurrentUser().getDisplayName()).child(activityName).child("Status");
                    activityValues.put("Status","PENDING");
                    databaseReference.setValue(activityValues);

                    activityValues = new HashMap();
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Club Activities").child(FirebaseAuth.getInstance()
                            .getCurrentUser().getDisplayName()).child(activityName).child("Creation");
                    activityValues.put("Creation",(new Date()).getTime()+"");
                    databaseReference.setValue(activityValues);

                    /////////// FOR ADMIN

                    activityValues = new HashMap();
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Approve Activities").child(FirebaseAuth.getInstance()
                            .getCurrentUser().getDisplayName()).child(activityName).child("GE");
                    activityValues.put("GE",ge.getText().toString());
                    databaseReference1.setValue(activityValues);

                    activityValues = new HashMap();
                    databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Approve Activities").child(FirebaseAuth.getInstance()
                            .getCurrentUser().getDisplayName()).child(activityName).child("Time");
                    activityValues.put("Time",time.getText().toString());
                    databaseReference1.setValue(activityValues);



                    activityValues = new HashMap();
                    databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Approve Activities").child(FirebaseAuth.getInstance()
                            .getCurrentUser().getDisplayName()).child(activityName).child("Date");
                    activityValues.put("Date",date.getText().toString());
                    databaseReference1.setValue(activityValues);


                    activityValues = new HashMap();
                    databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Approve Activities").child(FirebaseAuth.getInstance()
                            .getCurrentUser().getDisplayName()).child(activityName).child("Location");
                    activityValues.put("Location",loc.getText().toString());
                    databaseReference1.setValue(activityValues);

                    if (lang.getSelectedItem().toString().equals("English")){
                        activityValues = new HashMap();
                        databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Approve Activities").child(FirebaseAuth.getInstance()
                                .getCurrentUser().getDisplayName()).child(activityName).child("Language");
                        activityValues.put("Language","English");
                        databaseReference1.setValue(activityValues);

                    }
                    else{
                        activityValues = new HashMap();
                        databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Approve Activities").child(FirebaseAuth.getInstance()
                                .getCurrentUser().getDisplayName()).child(activityName).child("Language");
                        activityValues.put("Language","Turkish");
                        databaseReference1.setValue(activityValues);

                    }


                    activityValues = new HashMap();
                    databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Approve Activities").child(FirebaseAuth.getInstance()
                            .getCurrentUser().getDisplayName()).child(activityName).child("Description");
                    activityValues.put("Description",actDesc.getText().toString());
                    databaseReference1.setValue(activityValues);

                    activityValues = new HashMap();
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Club Activities").child(FirebaseAuth.getInstance()
                            .getCurrentUser().getDisplayName()).child(activityName).child("Creation");
                    activityValues.put("Creation",(new Date()).getTime()+"");
                    databaseReference.setValue(activityValues);

                    /////////////////////////////////////////////////////////////////

                    Toast.makeText(getActivity(),"Activity created and sent to Bilkent for approval", Toast.LENGTH_LONG).show();

                }
            }
        });


        return view;
    }
}
