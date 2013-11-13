package com.example.keddreader;

import android.app.Application;
import android.util.Log;

import com.example.keddreader.model.FeedSingleton;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.w("MY", "onCreate MyApp");

        FeedSingleton.initInstance();
    }

}