package com.example.keddreader;

import android.app.Application;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;

import com.example.keddreader.helper.FavDbHelper;
import com.example.keddreader.model.FeedSingleton;
import com.example.keddreader.service.FeedCheckerService;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class App extends Application {

    public static FavDbHelper favDbHelper;
    public static SQLiteDatabase writableFavDB;
    public static SQLiteDatabase readableFavDB;

    @Override
    public void onCreate() {
        super.onCreate();

        // Start feed updates checking service if
        if(PreferenceManager.getDefaultSharedPreferences(this).getBoolean("service_enabled", true)){
            // Start a service to check feed updates
            startService(new Intent(App.this, FeedCheckerService.class));
        }

        FeedSingleton.initInstance();

        ImageLoaderConfiguration config =
                new ImageLoaderConfiguration.Builder(getApplicationContext())
                        .denyCacheImageMultipleSizesInMemory()
                        .build();

        ImageLoader.getInstance().init(config);

        favDbHelper = new FavDbHelper(this);
        writableFavDB = favDbHelper.getWritableDatabase();
        readableFavDB = favDbHelper.getReadableDatabase();
    }

//    @Override
//    public void onTerminate(){
//        ImageLoader.getInstance().clearDiscCache();
//        ImageLoader.getInstance().clearMemoryCache();
//        super.onTerminate();
//    }

}