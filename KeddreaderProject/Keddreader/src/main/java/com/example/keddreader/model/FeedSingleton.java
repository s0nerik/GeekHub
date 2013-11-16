package com.example.keddreader.model;

public class FeedSingleton implements AsyncFeedGetter {
    private static volatile FeedSingleton instance;
    private Feed feed;
    private boolean feedAvailable = false;

    public static FeedSingleton getInstance(){
        return instance;
    }

    public static void initInstance(){
        instance = new FeedSingleton();
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
        this.feed.setInnerTitles();
        this.feed.setInnerContents();
        feedAvailable = true;
    }

}