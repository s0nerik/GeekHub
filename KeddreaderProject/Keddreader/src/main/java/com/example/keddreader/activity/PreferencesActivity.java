package com.example.keddreader.activity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;

import com.example.keddreader.R;
import com.example.keddreader.service.FeedCheckerService;

public class PreferencesActivity extends PreferenceActivity {

    CheckBoxPreference enable_service_chBox;
    PreferenceCategory service_settings;
    ListPreference check_interval_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        // Set variables to corresponding fields
        enable_service_chBox = (CheckBoxPreference) findPreference("service_enabled");
        check_interval_list = (ListPreference) findPreference("refresh_interval");
        service_settings = (PreferenceCategory) findPreference("service_settings");

        // Hide check interval settings when service is disabled
        enable_service_chBox.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                check_interval_list.setEnabled(enable_service_chBox.isChecked());
                return false;
            }
        });

        // Enable/Disable feed checking service
        enable_service_chBox.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                boolean enabled = (Boolean) newValue;
                if(enabled)
                    startService(new Intent(PreferencesActivity.this, FeedCheckerService.class));
                else
                    stopService(new Intent(PreferencesActivity.this, FeedCheckerService.class));
                return true;
            }
        });

        // Restart service when interval setting changed
        check_interval_list.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                stopService(new Intent(PreferencesActivity.this, FeedCheckerService.class));
                startService(new Intent(PreferencesActivity.this, FeedCheckerService.class));
                return true;
            }
        });

    }

}
