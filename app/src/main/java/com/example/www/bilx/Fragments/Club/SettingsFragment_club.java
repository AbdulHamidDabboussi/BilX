package com.example.www.bilx.Fragments.Club;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.SwitchPreferenceCompat;
import android.widget.Toast;

import com.example.www.bilx.Accounts.Club_Account;
import com.example.www.bilx.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 *  The settings fragment for the club class.
 *  @author Hanzallah Burney
 */

public class SettingsFragment_club extends PreferenceFragmentCompat  {
    private SwitchPreferenceCompat darkMode;
    private Preference resetPassword;
    private ListPreference language;
    private Preference profile;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        // Load the Preferences from the XML file
        addPreferencesFromResource(R.xml.club_preferences);
        resetPassword = (Preference) findPreference("password_reset");
        language = (ListPreference) findPreference("club_languages");
        profile = (Preference) findPreference("club_profile_picture");


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

        darkMode = (SwitchPreferenceCompat) findPreference("club_theme_mode");
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
                    Club_Account.isDark = true;
                    Club_Account.count++;
                    getActivity().recreate();
                }
                else {
                    Club_Account.isDark = false;
                    Club_Account.count++;
                    getActivity().recreate();
                }
                return true;
            }
        });
    }
}