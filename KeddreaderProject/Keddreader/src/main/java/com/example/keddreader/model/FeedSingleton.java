package com.example.keddreader.model;

import android.app.Activity;

public class FeedSingleton implements AsyncFeedGetter {
    private static volatile FeedSingleton instance;
    private Feed feed;
    private static Activity caller;
    private boolean feedAvailable = false;

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

    public void refreshFeed(AsyncFeedGetter caller){
        feedAvailable = false;
        new AsyncRSS(caller, this).execute("http://keddr.com/feed/");
    }

    public boolean feedAvailable(){
        return feedAvailable;
    }

    @Override
    public void onFeedParsed(Feed feed) {
        this.feed = feed;
        feedAvailable = true;
        this.feed.setInnerTitles();
        this.feed.setInnerContents();
    }

}