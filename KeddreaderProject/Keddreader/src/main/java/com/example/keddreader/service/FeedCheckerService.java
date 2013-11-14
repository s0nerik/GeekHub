package com.example.keddreader.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.example.keddreader.R;
import com.example.keddreader.activity.MainActivity;
import com.example.keddreader.model.AsyncFeedChecker;
import com.example.keddreader.model.AsyncRssCheck;

import java.util.Timer;
import java.util.TimerTask;

public class FeedCheckerService extends Service implements AsyncFeedChecker {

    private static final int NOTIFICATION_FEED_CHECKER_STARTED = 0;
    private static final int NOTIFICATION_FEED_UPDATED = 1;
    private static final long SERVICE_FIRST_START_DELAY = 10000L; // This is 10 seconds, which equals 10000 milliseconds
    private static int REFRESH_INTERVAL;
    private boolean runningFirstTime = true;
    private String lastBuildDate;
    private NotificationManager notificationManager;
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate(){
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        sharedPreferences = getSharedPreferences("updating_settings", MODE_PRIVATE);
        REFRESH_INTERVAL = sharedPreferences.getInt("REFRESH_TIME", 1);

        notifyFeedCheckerStarted();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
//                new AsyncRssCheck(FeedCheckerService.this).execute("https://dl.dropboxusercontent.com/u/9689938/test/test_feed.xml");
                new AsyncRssCheck(FeedCheckerService.this).execute("http://keddr.com/feed/");
            }
        }, SERVICE_FIRST_START_DELAY, REFRESH_INTERVAL * 60000); // Start task every REFRESH_INTERVAL minutes, first time run it after SERVICE_FIRST_START_DELAY

        return Service.START_STICKY;
    }

    @Override
    public void onFeedChecked(String lastBuildDate) {

        if(runningFirstTime){
            this.lastBuildDate = lastBuildDate;
            runningFirstTime = false;
        }else{

            // If last build time isn't equals local last build time, show notification
            if(!lastBuildDate.equals(this.lastBuildDate)){

                this.lastBuildDate = lastBuildDate;
                notifyFeedUpdated();

            }
        }
    }

    private void notifyFeedUpdated(){

        // Create a notification builder
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_action_refresh)
                        .setContentTitle("Keddr.com updated.")
                        .setAutoCancel(true)
                        .setContentText("Last update date: " + lastBuildDate);

        // Create intent which will start main activity when notification is clicked
        Intent resultIntent = new Intent(this, MainActivity.class);

        // Create back stack for intent
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(resultPendingIntent);

        // Issue a notification in the notification bar
        notificationManager.notify(NOTIFICATION_FEED_UPDATED, builder.build());

    }

    private void notifyFeedCheckerStarted(){

        // Create a notification builder
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setOngoing(true)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("Feed checker is running...")
                        .setContentText("You can turn it off in settings");

        // Create intent which will start main activity when notification is clicked
        Intent resultIntent = new Intent(this, MainActivity.class);

        // Create back stack for intent
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(resultPendingIntent);

        // Issue a notification in the notification bar
        notificationManager.notify(NOTIFICATION_FEED_CHECKER_STARTED, builder.build());

    }

}