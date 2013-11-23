package com.example.keddreader.model;

public class FeedSingleton implements AsyncFeedGetter {
    private static volatile FeedSingleton instance;
    private Feed feed;
    private boolean feedAvailable = false;
    private Article currentArticle;

    public static FeedSingleton getInstance(){
        return instance;
    }

    public static void initInstance(){
        instance = new FeedSingleton();
    }

    public Article getCurrentArticle(){
        return currentArticle;
    }

    public void setCurrentArticle(Article article){
        currentArticle = article;
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
        feed.setInnerData();
        feedAvailable = true;
    }

}