package com.example.keddreader.model;

import android.app.Activity;

public class FeedSingleton implements AsyncFeedGetter {
    private static volatile FeedSingleton instance;
    private Feed feed;
    private static Activity caller;

    private FeedSingleton(Activity activity) {
        if (activity instanceof AsyncFeedGetter){
            new AsyncRSS((AsyncFeedGetter) activity, this).execute("http://keddr.com/feed/");
        }
    }

    public static FeedSingleton getInstance(Activity activity) {
        FeedSingleton localInstance = instance;
        caller = activity;
        if (localInstance == null) {
            synchronized (FeedSingleton.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new FeedSingleton(activity);
                }
            }
        }
        return localInstance;
    }

    public Feed getFeed(){
        return feed;
    }

    public void refreshFeed(){
        if (caller instanceof AsyncFeedGetter){
            new AsyncRSS((AsyncFeedGetter) caller, this).execute("http://keddr.com/feed/");
        }
    }

    @Override
    public void onFeedParsed(Feed feed) {
        this.feed = feed;
    }

}