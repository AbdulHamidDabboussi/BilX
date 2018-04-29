package com.example.www.bilx.Fragments.Admin;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.SwitchPreferenceCompat;
import android.widget.Toast;

import com.example.www.bilx.Accounts.Admin_Account;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.example.www.bilx.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


/**
 *  The settings fragment for the admin class.
 *  @author Hanzallah Burney
 */

public class SettingsFragment_admin extends PreferenceFragmentCompat {
    private SwitchPreferenceCompat darkMode;
    private Preference resetPassword;
    private ListPreference language;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        // Load the Preferences from the XML file
        addPreferencesFromResource(R.xml.app_preferences);
        darkMode = (SwitchPreferenceCompat) findPreference("theme_mode");
        resetPassword = (Preference) findPreference("password_reset");
        language = (ListPreference) findPreference("admin_languages");

        // Implementation of dark mode
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Dark Mode")
                .child("admin");
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
                            .child("admin");
                    mode.put("Mode","true");
                    databaseReference.setValue(mode);
                    Admin_Account.count++;
                    getActivity().recreate();
                } else {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Dark Mode")
                            .child("admin");
                    mode.put("Mode","false");
                    databaseReference.setValue(mode);
                    Admin_Account.count++;
                    getActivity().recreate();
                }
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
                                    Toast.makeText(getActivity(), "Email sent!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(), "Enter valid Email!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                return true;
            }
        });
    }
}