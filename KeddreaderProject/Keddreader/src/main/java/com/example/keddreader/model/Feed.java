package com.example.keddreader.model;

import java.util.ArrayList;
import java.util.List;

public class Feed {

    private String current_title;
    private String current_content;
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

//    public String[] getTitles(){
//        ArrayList<String> titles = new ArrayList<>();
//        for (FeedMessage m:entries){
//            titles.add(m.getTitle());
//        }
//        return titles.toArray(new String[titles.size()]);
//    }

//    public String[] getLinks(){
//        ArrayList<String> links = new ArrayList<>();
//        for (FeedMessage m:entries){
//            links.add(m.getGuid());
//        }
//        return links.toArray(new String[links.size()]);
//    }

//    public String[] getContents(){
//        ArrayList<String> links = new ArrayList<>();
//        for (FeedMessage m:entries){
//            links.add(m.getContent());
//        }
//        return links.toArray(new String[links.size()]);
//    }

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
