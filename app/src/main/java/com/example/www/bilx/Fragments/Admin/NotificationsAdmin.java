package com.example.www.bilx.Fragments.Admin;

import android.app.Notification;
import android.app.NotificationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.www.bilx.R;


/**
 *  The settings fragment for the admin class.
 *  @author Hanzallah Burney
 */

public class NotificationsAdmin extends android.support.v4.app.Fragment {
    private Spinner admin_spinner;
    private EditText notify_text;
    private FloatingActionButton fab;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.notifcations_admin, container, false);

        admin_spinner = (Spinner) view.findViewById(R.id.sendTo_spinner);
        notify_text = (EditText) view.findViewById(R.id.Notification_text);
        fab = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);


        admin_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = parent.getItemAtPosition(pos);
                Toast.makeText(getActivity(), (String)item, Toast.LENGTH_LONG).show();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });




        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = notify_text.getText().toString();
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getActivity())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Bilkent Notification")
                        .setContentText(s)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                Notification notification = mBuilder.build();
                NotificationManagerCompat.from(getActivity()).notify(0,notification);
            }
        });



        return view;
    }
}
