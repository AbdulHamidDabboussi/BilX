package com.example.www.bilx.Fragments.Club;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.www.bilx.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CreateNewActivity extends Fragment {
    private EditText actName, ge, loc,actDesc, setDate, setTime;
    private Button time, date;
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
        time = (Button) view.findViewById(R.id.time);
        date = (Button) view.findViewById(R.id.date);
        loc = (EditText) view.findViewById(R.id.loc);
        lang = (Spinner) view.findViewById(R.id.lang_spinner);
        fab = (FloatingActionButton) view.findViewById(R.id.createAct_fab);
        setDate = (EditText) view.findViewById(R.id.setDate);
        setTime = (EditText) view.findViewById(R.id.setTime);

        // ===================== Time PICKER ========================================
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker();
            }
        });
        //===========================================================================
    // ===================== Date PICKER ========================================
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });

        //===========================================================================



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
                    activityValues.put("Time",setTime.getText().toString());
                    databaseReference.setValue(activityValues);




                    activityValues = new HashMap();
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Club Activities").child(FirebaseAuth.getInstance()
                            .getCurrentUser().getDisplayName()).child(activityName).child("Date");
                    activityValues.put("Date",setDate.getText().toString());
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

    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(getFragmentManager(), "Date Picker");

    }
    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            setDate.setText(String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear+1)
                    + "-" + String.valueOf(year));

        }
    };

    // TIME PICKER
    private void showTimePicker()
    {
        //DatePickerFragment date = new DatePickerFragment();
        TimePickerFragment time= new TimePickerFragment();

        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("hour", calender.HOUR_OF_DAY);
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("minute", calender.get(Calendar.MINUTE));
        time.setArguments(args);


        time.setCallBack(ontime);
        time.show(getFragmentManager(), "Time Picker");

    }

    TimePickerDialog.OnTimeSetListener ontime = new TimePickerDialog.OnTimeSetListener() {

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            setTime.setText(String.valueOf(hourOfDay) + ":" + String.valueOf(minute));
        }
    };

}
