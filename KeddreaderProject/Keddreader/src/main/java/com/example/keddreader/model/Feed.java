package com.example.keddreader.model;

import java.util.ArrayList;
import java.util.List;

public class Feed {

    private String current_title;
    private String current_content;

    final ArrayList<FeedMessage> entries = new ArrayList<>();

    public List<FeedMessage> getMessages() {
        return entries;
    }

    public void addMessage(FeedMessage msg){
        entries.add(msg);
    }

    public String[] getTitles(){
        ArrayList<String> titles = new ArrayList<>();
        for (FeedMessage m:entries){
            titles.add(m.getTitle());
        }
        return titles.toArray(new String[titles.size()]);
    }

    public String[] getLinks(){
        ArrayList<String> links = new ArrayList<>();
        for (FeedMessage m:entries){
            links.add(m.getGuid());
        }
        return links.toArray(new String[links.size()]);
    }

    public String[] getContents(){
        ArrayList<String> links = new ArrayList<>();
        for (FeedMessage m:entries){
            links.add(m.getContent());
        }
        return links.toArray(new String[links.size()]);
    }

    public void setCurrentContent(String current_content){
        this.current_content = current_content;
    }

    public String getCurrentContent(){
        return current_content;
    }

    public void setCurrentTitle(String current_title){
        this.current_title = current_title;
    }

    public String getCurrentTitle(){
        return current_title;
    }

}
