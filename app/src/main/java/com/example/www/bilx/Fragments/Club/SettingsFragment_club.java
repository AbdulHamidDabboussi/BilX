package com.example.www.bilx.Fragments.Club;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.SwitchPreferenceCompat;
import android.widget.Toast;

import com.example.www.bilx.Accounts.Admin_Account;
import com.example.www.bilx.Accounts.Club_Account;
import com.example.www.bilx.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 *  The settings fragment for the club class.
 *  @author Hanzallah Burney
 */

public class SettingsFragment_club extends PreferenceFragmentCompat  {
    private SwitchPreferenceCompat darkMode;
    private Preference resetPassword;
    private Preference reportBugs;
    private ListPreference language;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        // Load the Preferences from the XML file
        addPreferencesFromResource(R.xml.club_preferences);
        resetPassword = (Preference) findPreference("password_reset");
        reportBugs = (Preference) findPreference("club_report_bug");
        language = (ListPreference) findPreference("club_languages");


        // Reset Password
        resetPassword.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                FirebaseAuth firebaseauth = FirebaseAuth.getInstance();
                firebaseauth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText( getActivity(), "Email sent!", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText( getActivity(), "Enter valid Email!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                return true;
            }
        });

        // Report Bugs
        reportBugs.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {

                Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_SUBJECT, "BILX Bug Report");
                intent.setData(Uri.fromParts("mailto",
                        "azim.burney@ug.bilkent.edu.tr", null)); // or just "mailto:" for blank
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
                startActivity(intent);
                return true;
            }
        });


        // Implementation of change language
        language.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            // TODO CHANGE LANGUAGE IMPLEMENTATION
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if (language.getSummary().equals("English")){
                    Toast.makeText(getActivity(), "English", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getActivity(), "Turkish", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        // Implementation of dark mode
        darkMode = (SwitchPreferenceCompat) findPreference("club_theme_mode");

        if (FirebaseAuth.getInstance().getCurrentUser().getDisplayName() != null) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Dark Mode").
                    child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue().toString().contains("true")){
                        darkMode.setChecked(true);
                    }
                    else{
                        darkMode.setChecked(false);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            darkMode.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference pref, Object object) {
                    boolean isChecked = (Boolean) object;
                    Map mode = new HashMap();
                    if (isChecked) {
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Dark Mode")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                        mode.put("Mode","true");
                        databaseReference.setValue(mode);
                        Club_Account.count++;
                        getActivity().recreate();
                    } else {
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Dark Mode")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                        mode.put("Mode","false");
                        databaseReference.setValue(mode);
                        Club_Account.count++;
                        getActivity().recreate();
                    }
                    return true;
                }
            });
        }


    }
}