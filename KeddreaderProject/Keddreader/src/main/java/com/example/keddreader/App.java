package com.example.keddreader;

import android.app.Application;
import android.content.Intent;
import android.preference.PreferenceManager;

import com.example.keddreader.model.FeedSingleton;
import com.example.keddreader.service.FeedCheckerService;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Start feed updates checking service if
        if(PreferenceManager.getDefaultSharedPreferences(this).getBoolean("service_enabled", true)){
            // Start a service to check feed updates
            startService(new Intent(App.this, FeedCheckerService.class));
        }

        FeedSingleton.initInstance();
    }

}