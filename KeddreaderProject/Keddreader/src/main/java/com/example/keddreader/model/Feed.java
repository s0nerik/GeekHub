package com.example.keddreader.model;

import android.database.Cursor;

import com.example.keddreader.App;

import java.util.ArrayList;
import java.util.List;

public class Feed {

    private String current_title;
    private String current_content;
    private String current_author;
    private String current_pubDate;
    private String[] titles;
    private String[] contents;
    private String[] authors;
    private String[] pubDates;

    final ArrayList<FeedMessage> entries = new ArrayList<>();

    public List<FeedMessage> getMessages() {
        return entries;
    }

    public void addMessage(FeedMessage msg){
        entries.add(msg);
    }

    public String[] getTitles() {
        return titles;
    }

    public String[] getContents() {
        return contents;
    }

    public String[] getAuthors(){
        return authors;
    }

    public String[] getPubDates(){
        return pubDates;
    }

    public String getCurrentTitle(){
        return current_title;
    }

    public String getCurrentContent(){
        return current_content;
    }

    public String getCurrentAuthor(){
        return current_author;
    }

    public String getCurrentPubDate(){
        return current_pubDate;
    }

    public void setInnerTitles(){
        String[] titles = new String[entries.size()];
        for (int i=0; i<entries.size(); i++){
            titles[i] = entries.get(i).getTitle();
        }
        this.titles = titles;
    }

    public void setInnerContents(){
        String[] contents = new String[entries.size()];
        for (int i=0; i<entries.size(); i++){
            contents[i] = entries.get(i).getContent();
        }
        this.contents = contents;
    }

    public void setInnerAuthors(){
        String[] authors = new String[entries.size()];
        for (int i=0; i<entries.size(); i++){
            authors[i] = entries.get(i).getAuthor();
        }
        this.authors = authors;
    }

    public void setInnerPubDates(){
        String[] pubDates = new String[entries.size()];
        for (int i=0; i<entries.size(); i++){
            pubDates[i] = entries.get(i).getPubDate();
        }
        this.pubDates = pubDates;
    }

    public void setCurrentTitle(String current_title){
        this.current_title = current_title;
    }

    public void setCurrentContent(String current_content){
        this.current_content = current_content;
    }

    public void setCurrentAuthor(String current_author){
        this.current_author = current_author;
    }

    public void setCurrentPubDate(String current_pubDate){
        this.current_pubDate = current_pubDate;
    }

}
