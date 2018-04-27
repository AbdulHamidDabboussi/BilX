package com.example.www.bilx.Fragments.User;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.SwitchPreferenceCompat;
import android.widget.Toast;

import com.example.www.bilx.Accounts.User_Account;
import com.example.www.bilx.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 *  The settings fragment for the user class.
 *  @author Hanzallah Burney
 */

public class SettingsFragment_User extends PreferenceFragmentCompat  {
    private SwitchPreferenceCompat darkMode;
    private Preference resetPassword;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        // Load the Preferences from the XML file
        addPreferencesFromResource(R.xml.user_preferences);
        resetPassword = (Preference) findPreference("password_reset");

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


        darkMode = (SwitchPreferenceCompat) findPreference("user_theme_mode");
        if (darkMode.isChecked()){
            darkMode.setChecked(true);
        }
        else{
            darkMode.setChecked(false);
        }
        darkMode.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference pref, Object object) {
                boolean isChecked = (Boolean) object;
                if (isChecked) {
                    User_Account.isDark = true;
                    User_Account.count++;
                    getActivity().recreate();
                }
                else {
                    User_Account.isDark = false;
                    User_Account.count++;
                    getActivity().recreate();
                }
                return true;
            }
        });
    }
}